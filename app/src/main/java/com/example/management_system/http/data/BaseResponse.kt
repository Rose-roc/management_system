package com.example.management_system.http.data

import com.zhan.ktwing.ext.Toasts
import com.zhan.mvvm.bean.KResponse
import com.zhan.mvvm.config.Setting

data class BaseResponse<T>(var data: T?,
                           var code: Int = -1,
                           var msg: String = "") : KResponse<T> {

    override fun isSuccess(): Boolean = code == 200

    override fun getKData(): T? = data

    override fun getKMessage(): String? = msg

    private fun isLogout():Boolean = code == 401

    override fun executeRsp(
        successResponse: ((KResponse<T>) -> Unit)?,
        error: ((String) -> Unit)?
    ) {
        if (isLogout()){
            (this.getKMessage() ?: Setting.MESSAGE_EMPTY).let { error?.invoke(it) ?: Toasts.show(it) }
        }
        if (this.isSuccess()) {
            successResponse?.invoke(this)
            return
        }

        (this.getKMessage() ?: Setting.MESSAGE_EMPTY).let { error?.invoke(it) ?: Toasts.show(it) }
    }
}