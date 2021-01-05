package com.example.management_system.module.account

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.management_system.R
import com.example.management_system.module.main.MainActivity
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.ScreenUtil
import com.example.management_system.utils.SpUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.ktwing.ext.logd
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_login_or_register.*

class LoginOrRegisterActivity : AppCompatActivity(), IMvmActivity {
    @BindViewModel
    lateinit var viewModel: LoginOrRegisterViewModel

    lateinit var name: String

    override fun getLayoutId(): Int {
        return R.layout.activity_login_or_register
    }

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).init()
    }

    override fun initListener() {
        super.initListener()
        btn_log.setOnClickListener {
            showDialog(btn_log.text.toString(), true)
        }
        btn_reg.setOnClickListener {
            showDialog(btn_reg.text.toString(), false)
        }
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showDialog(text: String, isLog: Boolean) {
        val button1Dialog = Dialog(this, R.style.DialogTheme)
        val view: View = LayoutInflater.from(this).inflate(R.layout.login_dialog, null)

        val tvForgetPsw = view.findViewById<TextView>(R.id.tv_forget_password)
        if (isLog) tvForgetPsw.visibility = View.VISIBLE

        val etUserName = view.findViewById<AppCompatEditText>(R.id.username)
        val etPassword = view.findViewById<AppCompatEditText>(R.id.password)

        val btn = view.findViewById<Button>(R.id.btn_log_or_reg)
        btn.text = text
        btn.setOnClickListener {
            name = etUserName.text.toString()
            val password = etPassword.text.toString()
            logd("userName = $name, psw = $password")
            if (isLog) {
                logd("登录")
                viewModel.login(name, password)
            } else {
                logd("注册")
                viewModel.register(name, password)
            }
        }

        button1Dialog.setContentView(view)
        val layoutParams: ViewGroup.MarginLayoutParams =
            view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        layoutParams.height = ScreenUtil.getScreenHeight(this) / 5 * 3
        view.layoutParams = layoutParams
        button1Dialog.setCanceledOnTouchOutside(true)
        button1Dialog.window?.setGravity(Gravity.BOTTOM)
        button1Dialog.window?.setWindowAnimations(R.style.dialog_menu_animStyle)
        button1Dialog.show()
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            loginData.observe(this@LoginOrRegisterActivity, {
                logd("登录成功，跳转主界面")
                toast("登录成功")
                SpUtil.setString(SpUtil.USERNAME, name)
                ActivityUtil.startActivity(MainActivity::class.java, true)
            })
            loginData.observerError(this@LoginOrRegisterActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
            registerData.observe(this@LoginOrRegisterActivity, {
                logd("注册成功，跳转主界面")
                toast("注册成功")
                SpUtil.setString(SpUtil.USERNAME, name)
                ActivityUtil.startActivity(MainActivity::class.java, true)
            })
            registerData.observerError(this@LoginOrRegisterActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }
}