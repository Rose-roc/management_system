package com.example.management_system.module.owner

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.management_system.R
import com.example.management_system.utils.SpUtil
import com.example.management_system.utils.XUtil
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_search_history.*
import org.jetbrains.anko.alert

class SearchHistoryFragment : Fragment(), IMvmFragment, BaseQuickAdapter.OnItemLongClickListener,
    BaseQuickAdapter.OnItemChildClickListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_search_history
    }

    private val mAdapter by lazy {
        HistoryAdapter()
    }

    private var mRemoveMode = false

    override fun initView() {
        super.initView()

        rv_history.layoutManager = FlexboxLayoutManager(context)
        // 设置搜索历史整项的点击事件
        mAdapter.setOnItemClickListener { _, _, position ->
            if (!mRemoveMode) {
                val searchHistory = mAdapter.getItem(position)
                if (searchHistory != null) {
                    search(searchHistory)
                }
            }
        }
        mAdapter.onItemLongClickListener = this
        mAdapter.onItemChildClickListener = this
        rv_history.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        tv_down.setOnClickListener {
            changeRemoveMode(false)
        }
        tv_clean.setOnClickListener {
            context?.alert("确定要清除搜索历史？", "尊敬的用户") {
                positiveButton("确定清除") {
                    mAdapter.setNewData(null)
                    changeHistoryVisible()
                    saveHistory(null)
                }
                negativeButton("我再想想") { }
            }?.show()
        }
    }

    override fun initData() {
        mAdapter.setNewData(getHistory())
        changeHistoryVisible()
    }

    /**
     * 改变历史记录的显示
     */
    private fun changeHistoryVisible() {
        // 如果搜索历史数据为空的话，设置搜索历史不可见
        if (mAdapter.data.isEmpty()) {
            ll_history.visibility = View.GONE
        } else {
            ll_history.visibility = View.VISIBLE
        }
    }

    /**
     * 保存搜索历史
     */
    private fun saveHistory(list: List<String?>?) {
        var saves: List<String?>? = list
        // 设置搜索历史最多三十条
        val max = 30
        if (list != null && list.size > max) {
            saves = list.subList(0, max - 1)
        }
        val json = Gson().toJson(saves)
        SpUtil.setString(XUtil.getString(R.string.KEY_HISTORY), json)
    }

    /**
     * 得到搜索历史
     */
    private fun getHistory(): List<String?>? {
        val json: String = SpUtil.getString(XUtil.getString(R.string.KEY_HISTORY))
        return if (TextUtils.isEmpty(json)) {
            null
        } else Gson().fromJson<List<String?>>(
            json,
            object : TypeToken<List<String?>?>() {}.type
        )
    }

    /**
     * 改变清除模式
     *
     * @param removeMode 清除模式 ，true 显示完成 ，false 显示清除
     */
    private fun changeRemoveMode(removeMode: Boolean) {
        if (mRemoveMode == removeMode) {
            return
        }
        mRemoveMode = removeMode
        mAdapter.setRemoveModeChanging(true)
        mAdapter.setRemoveMode(mRemoveMode)
        mAdapter.notifyDataSetChanged()
        if (removeMode) {
            tv_down.visibility = View.VISIBLE
            tv_clean.visibility = View.GONE
        } else {
            tv_down.visibility = View.GONE
            tv_clean.visibility = View.VISIBLE
        }
    }

    /**
     * 搜索
     */
    private fun search(searchHistory: String) {
        if (activity is SearchActivity) {
            val activity = activity as SearchActivity?
            activity?.search(searchHistory)
        }
    }

    /**
     * 添加到搜索历史
     *
     * @param searchHistory 搜索历史
     */
    fun addHistory(searchHistory: String) {
        val dates: List<String> = mAdapter.data
        // 遍历搜索历史，如果发现有相同的历史话，就删除该历史
        var index = -1
        for (i in dates.indices) {
            if (dates[i] == searchHistory) {
                if (i == 0) {
                    return
                }
                if (i > 0) {
                    index = i
                }
            }
        }
        if(index > 0) mAdapter.remove(index)
        mAdapter.addData(0, searchHistory)
        val max = 30
        val list: List<String> = mAdapter.data
        if (list.size > max) {
            mAdapter.remove(list.size - 1)
        }
        saveHistory(mAdapter.data)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        mAdapter.remove(position)
        saveHistory(mAdapter.data)
        changeHistoryVisible()
    }

    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>?,
        view: View?,
        position: Int
    ): Boolean {
        changeRemoveMode(!mRemoveMode)
        return true
    }
}