package com.example.management_system.http.actuator

import com.zhan.mvvm.mvvm.actuator.impl.DefaultLiveDataActuator
import com.zhan.mvvm.mvvm.livedata.CommonLiveData

class MyLiveDataActuator : DefaultLiveDataActuator() {

    override fun <R> success(liveData: CommonLiveData<R>, data: R?) {
        super.success(liveData, data)
        // TODO
    }

    override fun <R> failure(liveData: CommonLiveData<R>, message: String?) {
        super.failure(liveData, message)
        // TODO
    }

    override fun <R> exception(liveData: CommonLiveData<R>, throwable: Throwable?) {
        super.exception(liveData, throwable)
    }
}