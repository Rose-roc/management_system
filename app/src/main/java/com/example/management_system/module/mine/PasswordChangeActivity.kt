package com.example.management_system.module.mine

import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.management_system.R
import com.example.management_system.utils.SpUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_password_change.*
import kotlinx.android.synthetic.main.title_bar.*

class PasswordChangeActivity : AppCompatActivity(), IMvmActivity, View.OnClickListener {
    @BindViewModel
    lateinit var viewModel: PasswordViewModel

    override fun getLayoutId(): Int = R.layout.activity_password_change

    override fun initView() {
        title_bar_text.text = "密码修改"
        ImmersionBar.with(this).titleBar(title_bar).init()
    }

    override fun initListener() {
        title_bar_return.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        cb_show_password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            title_bar_return -> finish()
            btn_confirm -> {
                if (TextUtils.isEmpty(et_new_password.text.toString()) || TextUtils.isEmpty(
                        et_confirm_new_password.text.toString()
                    )
                ) {
                    toast("密码不能为空", Toast.LENGTH_SHORT)
                    return
                }

                if (et_new_password.text.toString() != et_confirm_new_password.text.toString()) {
                    toast("两次密码必须一致", Toast.LENGTH_SHORT)
                    return
                }
                viewModel.resetPassword(
                    SpUtil.getString(SpUtil.USERNAME),
                    et_confirm_new_password.text.toString()
                )
            }
            cb_show_password -> {
                if (cb_show_password.isChecked) {
                    //显示
                    et_new_password.transformationMethod = HideReturnsTransformationMethod()
                    et_confirm_new_password.transformationMethod =
                        HideReturnsTransformationMethod()
                } else {
                    et_new_password.transformationMethod = PasswordTransformationMethod()
                    et_confirm_new_password.transformationMethod =
                        PasswordTransformationMethod()
                }
            }
        }
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            userPasswordData.observe(this@PasswordChangeActivity, {
                toast("修改密码成功")
                finish()
            })

            // 监听错误信息 包括 Exception 和 Failure
            userPasswordData.observerError(this@PasswordChangeActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }

}