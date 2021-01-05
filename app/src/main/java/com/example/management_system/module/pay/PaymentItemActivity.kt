package com.example.management_system.module.pay

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.data.PaymentItem
import com.example.management_system.utils.ScreenUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.ktwing.ext.logd
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_payment_item.*
import kotlinx.android.synthetic.main.rv_item_payment_item.*
import kotlinx.android.synthetic.main.title_bar.*
import org.jetbrains.anko.alert

class PaymentItemActivity : AppCompatActivity(), IMvmActivity,
    SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_payment_item
    }

    private val mAdapter by lazy {
        PaymentItemAdapter()
    }

    private var position: Int = 0
    lateinit var item: PaymentItem

    @BindViewModel
    lateinit var viewModel: PaymentItemViewModel

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(title_bar).init()
        title_bar_text.text = "收费项目信息"

        initAdapter()
        initRv()
        initSrl()
        viewModel.findPaymentItemList()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun initListener() {
        super.initListener()
        title_bar_return.setOnClickListener {
            finish()
        }
        iv_add_payment_item.setOnClickListener {
            val dialog = PaymentItemDialog(this, object : PaymentItemDialog.ClickCallBack {
                override fun yesClick(dialog: PaymentItemDialog) {
                    val name: String = dialog.etName?.text.toString()
                    val price: String = dialog.etPrice?.text.toString()
                    val unit: String = dialog.etUnit?.text.toString()
                    if (name == "") {
                        toast("名称不能为空")
                        return
                    }
                    if (price == "") {
                        toast("价格不能为空")
                        return
                    }
                    if (unit == "") {
                        toast("单位不能为空")
                        return
                    }
                    logd("确定")
                    item = PaymentItem(name, price.toFloat(), unit)
                    viewModel.insertPaymentItem(name, price, unit)
                    dialog.dismiss()
                }
            })
            dialog.show()
        }
    }

    private fun initSrl() {
        srl_payment_item.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@PaymentItemActivity)
        }
    }

    private fun initRv() {
        rv_payment_item.run {
            layoutManager = GridLayoutManager(this@PaymentItemActivity, 2)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemChildClickListener = this@PaymentItemActivity
        }
    }

    override fun onRefresh() {
        viewModel.findPaymentItemList()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            payItemListData.observe(this@PaymentItemActivity, {
                mAdapter.setNewData(it)
                srl_payment_item.isRefreshing = false
            })
            payItemListData.observerError(this@PaymentItemActivity, {
                srl_payment_item.isRefreshing = false
                toast(it?.message.toString())
            }, {
                srl_payment_item.isRefreshing = false
                it?.let { it1 -> toast(it1) }
            })
            payItemDeleteData.observe(this@PaymentItemActivity, {
                toast(it)
                mAdapter.remove(position)
            })
            payItemDeleteData.observerError(this@PaymentItemActivity, {
                toast("不能删除")
            }, {
                it?.let { it1 -> toast(it1) }
            })
            payItemAddData.observe(this@PaymentItemActivity, {
                toast(it)
                mAdapter.addData(item)
            })
            payItemAddData.observerError(this@PaymentItemActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
            payItemUpdateData.observe(this@PaymentItemActivity, {
                toast(it)
                mAdapter.setData(position, item)
            })
            payItemUpdateData.observerError(this@PaymentItemActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, v: View?, pos: Int) {
        position = pos
        when (v?.id) {
            R.id.tv_payment_item_name -> {
                val paymentItem = mAdapter.data[pos]
                val dialog = PaymentItemDialog(
                    this,
                    paymentItem.paymentItem,
                    paymentItem.price.toString(),
                    paymentItem.unit,
                    object : PaymentItemDialog.ClickCallBack {
                        override fun yesClick(dialog: PaymentItemDialog) {
                            var price: String = dialog.etPrice?.text.toString()
                            var unit: String = dialog.etUnit?.text.toString()
                            if (price == "") {
                                price = paymentItem.price.toString()
                            }
                            if (unit == "") {
                                unit = paymentItem.unit
                            }
                            logd("确定")
                            item = PaymentItem(paymentItem.paymentItem, price.toFloat(), unit)
                            viewModel.updatePaymentItem(paymentItem.paymentItem, price, unit)
                            dialog.dismiss()
                        }
                    }, false
                )
                dialog.show()
            }
            R.id.iv_delete -> alert("确定删除该收费项目吗？", "尊敬的用户") {
                positiveButton("确定删除") {
                    viewModel.deletePaymentItemByName(mAdapter.data[pos].paymentItem)
                }
                negativeButton("我再想想") { }
            }.show()
        }
    }

}