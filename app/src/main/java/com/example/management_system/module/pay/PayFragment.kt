package com.example.management_system.module.pay

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.data.Pay
import com.example.management_system.utils.ActivityUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.ktwing.ext.logd
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.android.synthetic.main.title_bar.*
import org.jetbrains.anko.alert

class PayFragment : Fragment(), IMvmFragment,
    BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_pay
    }

    private val mAdapter by lazy {
        PayAdapter()
    }

    private var mPayList = mutableListOf<Pay>()

    var position: Int = 0

    @BindViewModel
    lateinit var viewModel: PayViewModel

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(title_bar).init()
        title_bar_return.visibility = View.GONE
        title_bar_text.text = "收费信息"

        initAdapter()
        initRv()
        initSrl()

        viewModel.getPayList()
    }

    private fun initSrl() {
        srl_pay.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@PayFragment)
        }
    }

    private fun initRv() {
        rv_pay.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemChildClickListener = this@PayFragment
        }
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, pos: Int) {
        when (view?.id) {
            R.id.iv_delete -> {
                logd("删除")
                position = pos
                context?.alert("确定删除该收费信息吗？", "尊敬的用户") {
                    positiveButton("确定删除") { viewModel.deletePayById(mPayList[pos].payId) }
                    negativeButton("我再想想") { }
                }?.show()
            }
            R.id.tv_payment_item_name -> {
                logd("进入paymentItem界面")
                ActivityUtil.startActivity(PaymentItemActivity::class.java)
            }
        }
    }

    override fun onRefresh() {
        viewModel.getPayList()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            payListData.observe(this@PayFragment, {
                mPayList = it.toMutableList()
                mAdapter.setNewData(it)
                srl_pay.isRefreshing = false
            })
            payListData.observerError(this@PayFragment, {
                toast(it?.message.toString())
                srl_pay.isRefreshing = false
            }, {
                srl_pay.isRefreshing = false
                it?.let { it1 -> toast(it1) }
            })
            payDeleteData.observe(this@PayFragment, {
                toast(it)
                mPayList.removeAt(position)
                mAdapter.remove(position)
            })
            payDeleteData.observerError(this@PayFragment, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }
}