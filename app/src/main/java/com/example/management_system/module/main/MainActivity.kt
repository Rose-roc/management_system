package com.example.management_system.module.main

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.management_system.R
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.BottomNavigationViewUtil
import com.example.management_system.utils.XUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMvmActivity {

    companion object {
        const val OVER_TIME = 2000
    }

    private var mExitTime: Long = 0

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()

        ImmersionBar.with(this).init()

        BottomNavigationViewUtil.disableShiftMode(bnv_main)

        val navController = findNavController(R.id.nav_host_fragment)
        bnv_main.setupWithNavController(navController)
    }

    /**
     * 根据当前时间节点判断是否退出,双击退出功能的设置
     */
    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > OVER_TIME) {
            Toasts.show(
                resources.getString(R.string.double_quit) + resources.getString(
                    R.string.app_name
                )
            )
            mExitTime = System.currentTimeMillis()
        } else {
            ActivityUtil.closeAllActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        XUtil.dismissLoading()
    }
}