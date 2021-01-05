package com.example.management_system.module.house

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.base.GeneralRequestState
import com.example.management_system.data.House
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.EmptyViewUtil
import com.example.management_system.utils.XUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.bean.SharedType
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_house.*
import kotlinx.android.synthetic.main.fragment_owner.*
import kotlinx.android.synthetic.main.title_bar.*

class HouseFragment:Fragment(),IMvmFragment, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_house
    }

    private val mAdapter by lazy {
        HouseAdapter()
    }

    @BindViewModel
    lateinit var viewModel: HouseViewModel

    private var mHouseList = mutableListOf<House>()

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(title_bar).init()
        title_bar_return.visibility = View.GONE
        title_bar_right_text.visibility = View.VISIBLE
        title_bar_right_text.text = "统计"
        title_bar_text.text = "房屋信息"

        initAdapter()
        initRv()
        initSrl()
    }

    override fun initListener() {
        super.initListener()
        title_bar_right_text.setOnClickListener {
            ActivityUtil.startActivity(HousingStatisticsActivity::class.java)
        }
    }

    override fun initData() {
        super.initData()
        viewModel.onRefresh()
    }

    private fun initSrl() {
        srl_house.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@HouseFragment)
        }
    }

    private fun initRv() {
        rv_house.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemChildClickListener = this@HouseFragment
            setOnLoadMoreListener(this@HouseFragment, rv_house)
        }
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    override fun onItemChildClick(p0: BaseQuickAdapter<*, *>?, p1: View?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onLoadMoreRequested() {
        viewModel.onLoadMore()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            loadState.observe(this@HouseFragment, {
                when (it) {
                    GeneralRequestState.LOADER_MORE_EMPTY -> {
                        srl_house.isRefreshing = false
                        mAdapter.loadMoreEnd()
                    }
                    GeneralRequestState.SUCCESS -> {
                        mHouseList.clear()
                        houseListData.value?.list?.let { it1 -> mHouseList.addAll(it1) }
                        mAdapter.notifyLoadMoreToLoading()
                        mAdapter.data = mHouseList
                        srl_house.isRefreshing = false
                    }
                    GeneralRequestState.LOADER_MORE_SUCCESS -> {
                        mHouseList.clear()
                        houseListData.value?.list?.let { it1 -> mHouseList.addAll(it1) }
                        mAdapter.data = mHouseList
                        mAdapter.loadMoreComplete()
                    }
                    GeneralRequestState.EMPTY -> {
                        if (srl_owner.isRefreshing) srl_owner.isRefreshing = false
                        mAdapter.emptyView = EmptyViewUtil.getEmptyDataView(rv_owner)
                    }
                    else -> {
                    }
                }
            })

            houseListData.observerError(this@HouseFragment, {
                toast(it?.message.toString())
                finishRefreshAndLoadMoreError()
            }, {
                finishRefreshAndLoadMoreError()
                it?.let { it1 -> toast(it1) }
            })

            // 监听loading
            sharedData.observe(this@HouseFragment, {
                if (it.type == SharedType.SHOW_LOADING) XUtil.showLoading(XUtil.getString(R.string.loading))
                else if (it.type == SharedType.HIDE_LOADING) XUtil.dismissLoading()
            })
        }
    }

    private fun finishRefreshAndLoadMoreError() {
        srl_house.run {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
        if (mAdapter.isLoading) {
            mAdapter.loadMoreFail()
        }
    }

}