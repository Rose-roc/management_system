package com.example.management_system.module.maintain

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.module.pay.PayAdapter
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_maintain.*
import kotlinx.android.synthetic.main.fragment_pay.*
import kotlinx.android.synthetic.main.fragment_pay.srl_pay
import kotlinx.android.synthetic.main.title_bar.*
import org.jetbrains.anko.alert

class MaintainFragment : Fragment(), IMvmFragment, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.OnItemLongClickListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_maintain
    }

    private val mAdapter by lazy {
        MaintainAdapter()
    }

    var position:Int = 0

    @BindViewModel
    lateinit var viewModel: MaintainViewModel

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(title_bar).init()
        title_bar_return.visibility = View.GONE
        title_bar_text.text = "维修信息"

        initAdapter()
        initRv()
        initSrl()

        viewModel.getAllMaintainList()
    }

    private fun initSrl() {
        srl_maintain.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@MaintainFragment)
        }
    }

    private fun initRv() {
        rv_maintain.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemLongClickListener = this@MaintainFragment
        }
    }

    override fun onRefresh() {
        viewModel.getAllMaintainList()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            maintainListData.observe(this@MaintainFragment, {
                mAdapter.setNewData(it)
                srl_maintain.isRefreshing = false
            })
            maintainListData.observerError(this@MaintainFragment, {
                toast(it?.message.toString())
                srl_maintain.isRefreshing = false
            }, {
                it?.let { it1 -> toast(it1) }
                srl_maintain.isRefreshing = false
            })
            maintainDeleteData.observe(this@MaintainFragment, {
                toast(it)
                mAdapter.remove(position)
            })
            maintainDeleteData.observerError(this@MaintainFragment, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }

    override fun onItemLongClick(p0: BaseQuickAdapter<*, *>?, p1: View?, pos: Int): Boolean {
        position = pos
        context?.alert("确定删除该维修信息吗？", "尊敬的用户") {
            positiveButton("确定删除") { viewModel.deleteMaintainById(mAdapter.data[pos].maintainId) }
            negativeButton("我再想想") { }
        }?.show()
        return true
    }
}