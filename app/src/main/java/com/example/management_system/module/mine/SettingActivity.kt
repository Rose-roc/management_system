package com.example.management_system.module.mine

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.management_system.R
import com.example.management_system.module.account.LoginOrRegisterActivity
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.SpUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.title_bar.*

@SuppressLint("SetTextI18n")
class SettingActivity : AppCompatActivity(), IMvmActivity, View.OnClickListener {

    override fun initView() {
        title_bar_text.text = "账号设置"
        ImmersionBar.with(this).titleBar(title_bar).init()

        tv_user_name.text = SpUtil.getString(SpUtil.USERNAME)
    }

    override fun initListener() {
        title_bar_return.setOnClickListener(this)
        rl_personal_info.setOnClickListener(this)
        modify_password.setOnClickListener(this)
        btn_log_out.setOnClickListener(this)
    }


    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun onClick(v: View?) {
        when (v) {
            title_bar_return -> finish()
            modify_password ->  ActivityUtil.startActivity(PasswordChangeActivity::class.java)
            btn_log_out -> {
                ActivityUtil.startActivity(LoginOrRegisterActivity::class.java)
                ActivityUtil.closeAllActivity()
            }
        }
    }
}