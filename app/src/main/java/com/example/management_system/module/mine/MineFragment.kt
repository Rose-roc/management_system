package com.example.management_system.module.mine

import androidx.fragment.app.Fragment
import com.example.management_system.R
import com.example.management_system.utils.ActivityUtil
import com.example.management_system.utils.SpUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.mvvm.mvvm.IMvmFragment
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment(), IMvmFragment{

    override fun initView() {
        ImmersionBar.with(this).init()
        tv_user_name.text = SpUtil.getString(SpUtil.USERNAME)
    }


    override fun initListener() {
        super.initListener()
        rl_setting.setOnClickListener{
            ActivityUtil.startActivity(SettingActivity::class.java)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine
}