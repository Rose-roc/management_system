package com.example.management_system.module.owner

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.base.GeneralRequestState
import com.example.management_system.data.Owner
import com.example.management_system.utils.EmptyViewUtil
import com.example.management_system.utils.XUtil
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.bean.SharedType
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_owner.*
import kotlinx.android.synthetic.main.fragment_search_result.*

class SearchResultFragment : Fragment(), IMvmFragment, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    @BindViewModel
    lateinit var viewModel: SearchResultViewModel

    private var mKey: String? = null

    private val mAdapter by lazy {
        OwnerAdapter()
    }

    private var mOwnerList = mutableListOf<Owner>()
    var position: Int = 0

    override fun initView() {
        super.initView()

        initAdapter()
        initRv()
        initSrl()
    }

    override fun initData() {
        super.initData()
        val bundle = arguments
        val str = bundle!!.getString("Key","null")
        search(str)
    }

    /**
     * 搜索
     *
     * @param searchHistory 搜索历史
     */
    fun search(searchHistory: String) {
        mAdapter.setNewData(null)
        mKey = searchHistory
        onRefresh()
    }

    private fun initSrl() {
        srl_search_result.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@SearchResultFragment)
        }
    }

    private fun initRv() {
        rv_search_result.run {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemChildClickListener = this@SearchResultFragment
            setOnLoadMoreListener(this@SearchResultFragment, rv_owner)
        }
    }

    override fun onRefresh() {
        viewModel.onRefresh(mKey!!)
    }

    override fun onItemChildClick(p0: BaseQuickAdapter<*, *>?, p1: View?, pos: Int) {
        position = pos
        val intent = Intent(context, OwnerDetailActivity::class.java).apply {
            putExtra("owner", mOwnerList[pos])
        }
        this.startActivityForResult(intent, OwnerFragment.REQUEST_CODE)
    }

    override fun onLoadMoreRequested() {
        viewModel.onLoadMore(mKey!!)
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            loadState.observe(this@SearchResultFragment, {
                when (it) {
                    GeneralRequestState.LOADER_MORE_EMPTY -> {
                        srl_search_result.isRefreshing = false
                        mAdapter.loadMoreEnd()
                    }
                    GeneralRequestState.SUCCESS -> {
                        mOwnerList.clear()
                        ownerListData.value?.list?.let { it1 -> mOwnerList.addAll(it1) }
                        mAdapter.notifyLoadMoreToLoading()
                        mAdapter.data = mOwnerList
                        srl_search_result.isRefreshing = false
                    }
                    GeneralRequestState.LOADER_MORE_SUCCESS -> {
                        mOwnerList.clear()
                        ownerListData.value?.list?.let { it1 -> mOwnerList.addAll(it1) }
                        mAdapter.data = mOwnerList
                        mAdapter.loadMoreComplete()
                    }
                    GeneralRequestState.EMPTY -> {
                        if (srl_search_result.isRefreshing) srl_search_result.isRefreshing = false
                        mAdapter.emptyView = EmptyViewUtil.getEmptyDataView(rv_owner)
                    }
                    else -> {
                    }
                }
            })

            ownerListData.observerError(this@SearchResultFragment, {
                toast(it?.message.toString())
                finishRefreshAndLoadMoreError()
            }, {
                finishRefreshAndLoadMoreError()
                it?.let { it1 -> toast(it1) }
            })

            // 监听loading
            sharedData.observe(this@SearchResultFragment, {
                if (it.type == SharedType.SHOW_LOADING) XUtil.showLoading(XUtil.getString(R.string.loading))
                else if (it.type == SharedType.HIDE_LOADING) XUtil.dismissLoading()
            })
        }
    }

    private fun finishRefreshAndLoadMoreError() {
        srl_search_result.run {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
        if (mAdapter.isLoading) {
            mAdapter.loadMoreFail()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        when (requestCode) {
            OwnerFragment.REQUEST_CODE -> {
                when (resultCode) {
                    OwnerDetailActivity.DELETE_SUCCESS -> mAdapter.remove(position)
                    OwnerDetailActivity.MODIFY_PHONE_SUCCESS -> {
                        val phone = intent?.getStringExtra("phone")
                        mAdapter.setPhone(position, phone)
                    }
                }
            }
        }
    }
}