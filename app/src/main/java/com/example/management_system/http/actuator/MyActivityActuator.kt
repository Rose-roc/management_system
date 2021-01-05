package com.example.management_system.http.actuator

import com.zhan.mvvm.mvvm.IMvmView
import com.zhan.mvvm.mvvm.actuator.impl.DefaultActivityActuator

class MyActivityActuator : DefaultActivityActuator() {

    override fun <R> success(mvmView: IMvmView, data: R?) {
        super.success(mvmView, data)
        // TODO
    }

    override fun failure(mvmView: IMvmView, message: String?) {
        super.failure(mvmView, message)
        // TODO
    }

    override fun exception(mvmView: IMvmView, throwable: Throwable?) {
        super.exception(mvmView, throwable)
        // TODO
    }
}