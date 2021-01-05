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
import com.example.management_system.module.owner.OwnerDetailActivity.Companion.DELETE_SUCCESS
import com.example.management_system.module.owner.OwnerDetailActivity.Companion.MODIFY_PHONE_SUCCESS
import com.example.management_system.utils.*
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.bean.SharedType
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_owner.*
import kotlinx.android.synthetic.main.title_bar.*


class OwnerFragment : Fragment(), IMvmFragment, SwipeRefreshLayout.OnRefreshListener,
    BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {
    companion object {
        const val REQUEST_CODE = 11
    }

    @BindViewModel
    lateinit var viewModel: OwnerViewModel

    override fun getLayoutId(): Int = R.layout.fragment_owner

    private val mAdapter by lazy {
        OwnerAdapter()
    }

    private var mOwnerList = mutableListOf<Owner>()
    var position: Int = 0

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(include).init()
        title_bar_return.visibility = View.GONE
        title_bar_right_image.visibility = View.VISIBLE
        ImageLoader.loadLocalIcon(
            title_bar_right_image,
            R.drawable.ic_search
        )
        title_bar_text.text = "业主信息"

        initAdapter()
        initRv()
        initSrl()
    }

    override fun initListener() {
        super.initListener()
        title_bar_right_image.setOnClickListener {
            ActivityUtil.startActivity(SearchActivity::class.java)
        }
    }


    override fun initData() {
        super.initData()
        viewModel.onRefresh()
        SpUtil.setInt("1",R.drawable.avatar01)
        SpUtil.setInt("2",R.drawable.avatar02)
        SpUtil.setInt("3",R.drawable.avatar03)
        SpUtil.setInt("4",R.drawable.avatar04)
        SpUtil.setInt("5",R.drawable.avatar05)
        SpUtil.setInt("6",R.drawable.avatar06)
        SpUtil.setInt("7",R.drawable.avatar07)
        SpUtil.setInt("8",R.drawable.avatar08)
        SpUtil.setInt("9",R.drawable.avatar09)
    }

    private fun initSrl() {
        srl_owner.run {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener(this@OwnerFragment)
        }
    }

    private fun initRv() {
        rv_owner.run {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = mAdapter
        }
    }

    private fun initAdapter() {
        // 设置 adapter 的参数
        mAdapter.run {
            setEnableLoadMore(true)
            openLoadAnimation()
            onItemChildClickListener = this@OwnerFragment
            setOnLoadMoreListener(this@OwnerFragment, rv_owner)
        }
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            loadState.observe(this@OwnerFragment, {
                when (it) {
                    GeneralRequestState.LOADER_MORE_EMPTY -> {
                        srl_owner.isRefreshing = false
                        mAdapter.loadMoreEnd()
                    }
                    GeneralRequestState.SUCCESS -> {
                        mOwnerList.clear()
                        ownerListData.value?.list?.let { it1 -> mOwnerList.addAll(it1) }
                        mAdapter.notifyLoadMoreToLoading()
                        mAdapter.data = mOwnerList
                        srl_owner.isRefreshing = false
                    }
                    GeneralRequestState.LOADER_MORE_SUCCESS -> {
                        mOwnerList.clear()
                        ownerListData.value?.list?.let { it1 -> mOwnerList.addAll(it1) }
                        mAdapter.data = mOwnerList
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

            ownerListData.observerError(this@OwnerFragment, {
                toast(it?.message.toString())
                finishRefreshAndLoadMoreError()
            }, {
                finishRefreshAndLoadMoreError()
                it?.let { it1 -> toast(it1) }
            })

            // 监听loading
            sharedData.observe(this@OwnerFragment, {
                if (it.type == SharedType.SHOW_LOADING) XUtil.showLoading(XUtil.getString(R.string.loading))
                else if (it.type == SharedType.HIDE_LOADING) XUtil.dismissLoading()
            })
        }
    }

    private fun finishRefreshAndLoadMoreError() {
        srl_owner.run {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
        if (mAdapter.isLoading) {
            mAdapter.loadMoreFail()
        }
    }

    override fun onLoadMoreRequested() {
        viewModel.onLoadMore()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> {
                when(resultCode){
                    DELETE_SUCCESS ->  mAdapter.remove(position)
                    MODIFY_PHONE_SUCCESS -> {
                        val phone = intent?.getStringExtra("phone")
                        mAdapter.setPhone(position, phone)
                    }
                }
            }
        }
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, v: View?, pos: Int) {
        position = pos
        val intent = Intent(context, OwnerDetailActivity::class.java).apply {
            putExtra("owner", mOwnerList[pos])
        }
        this.startActivityForResult(intent, REQUEST_CODE)
    }
}