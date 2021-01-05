package com.example.management_system.base

import android.app.Application
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.LogUtil
import com.example.management_system.utils.XUtil
import com.zhan.mvvm.KtArmor
import com.example.management_system.http.MyRetrofitConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(ActivityUtil.getActivityLifecycleCallbacks())
        //初始化
        XUtil.initialize(this)
        //设置打印开关
        LogUtil.setIsLog(true)
        with(KtArmor) {
            // 初始化KtArmor
            initRetrofitConfig(MyRetrofitConfig())
            // 打印 KtArmor 内部 log
            printLog = true
        }

    }
}