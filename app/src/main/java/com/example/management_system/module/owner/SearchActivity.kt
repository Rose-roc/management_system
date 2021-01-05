package com.example.management_system.module.owner

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.management_system.R
import com.example.management_system.utils.XUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.logd
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), IMvmActivity {
    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    private var mIsResultPage = false
    lateinit var searchHistoryFragment: SearchHistoryFragment

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(ll_search_title).init()
        et_search.isSingleLine = true
        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH

        et_search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(et_search.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun initListener() {
        super.initListener()
        tv_search.setOnClickListener {
            search(et_search.text.toString())
        }
        iv_back.setOnClickListener {
            // 如果现在是结果的页面的话，按返回键，显示的是历史的 Fragment
            if (mIsResultPage) {
                mIsResultPage = !mIsResultPage
                onBackPressed()
            } else {
                finish()
            }
        }
    }

    fun search(searchHistory: String) {
        //隐藏虚拟键盘
        XUtil.closeSoftKeyboard()
        et_search.clearFocus()
        if (TextUtils.isEmpty(searchHistory)) {
            // 如果现在是搜索结果界面的话，返回搜索历史界面
            if (mIsResultPage) {
                mIsResultPage = !mIsResultPage
                onBackPressed()
            }
        } else {
            et_search.setText(searchHistory)
            et_search.setSelection(searchHistory.length)

            if (!mIsResultPage) {
                mIsResultPage = !mIsResultPage
                val bundle = Bundle()
                bundle.putString("Key", searchHistory)

                Navigation.findNavController(this, R.id.search_fragment)
                    .navigate(R.id.action_search_result, bundle)
            }

            //获取指定的fragment
            var fragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.search_fragment)?.childFragmentManager?.primaryNavigationFragment
            if (fragment is SearchHistoryFragment) {
                searchHistoryFragment = fragment
            }
            searchHistoryFragment.addHistory(searchHistory)

            if (fragment is SearchResultFragment) {
                fragment.search(searchHistory)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mIsResultPage) {
            mIsResultPage = !mIsResultPage
        }
    }
}