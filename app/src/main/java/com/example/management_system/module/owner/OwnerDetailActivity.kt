package com.example.management_system.module.owner

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.management_system.R
import com.example.management_system.data.Family
import com.example.management_system.data.House
import com.example.management_system.data.Owner
import com.example.management_system.utils.ImageLoader
import com.example.management_system.utils.ScreenUtil
import com.example.management_system.utils.SpUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.ktwing.ext.logd
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_owner_detail.*
import kotlinx.android.synthetic.main.title_bar.*

class OwnerDetailActivity : AppCompatActivity(), IMvmActivity {
    companion object {
        const val DELETE_SUCCESS = 101
        const val MODIFY_PHONE_SUCCESS = 102
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_owner_detail
    }

    @BindViewModel
    lateinit var viewModel: OwnerDetailViewModel

    lateinit var owner: Owner
    private lateinit var dialog: Dialog
    private var phone: String = ""

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(R.id.title_bar).init()
        title_bar_text.text = "业主详细信息"
        title_bar_right_text.visibility = View.VISIBLE
        title_bar_right_text.text = "删除"

        owner = intent.getParcelableExtra("owner")!!
        tv_owner_name.text = owner.name
        tv_owner_phone.text = owner.phone

        ImageLoader.loadLocalIcon(
            iv_avatar,
            SpUtil.getInt((owner.ownerId % 9 + 1).toString())
        )

        viewModel.findHouseById(owner.houseId)
        viewModel.findFamilyById(owner.familyId)
    }

    override fun initListener() {
        super.initListener()
        title_bar_return.setOnClickListener {
            finish()
        }
        title_bar_right_text.setOnClickListener {
            viewModel.deleteOwner(owner.ownerId)
        }
        tv_owner_phone.setOnClickListener {
            showDialog()
        }
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            houseData.observe(this@OwnerDetailActivity, {
                setHouseView(it)
            })
            houseData.observerError(this@OwnerDetailActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })

            familyData.observe(this@OwnerDetailActivity, {
                setFamilyView(it)
            })
            familyData.observerError(this@OwnerDetailActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
            ownerDeleteData.observe(this@OwnerDetailActivity, {
                val intentTemp = Intent()
                setResult(DELETE_SUCCESS, intentTemp)
                toast("删除成功")
                finish()
            })
            ownerDeleteData.observerError(this@OwnerDetailActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
            ownerUpdateData.observe(this@OwnerDetailActivity, {
                val intentTemp = Intent()
                intentTemp.putExtra("phone", phone)
                setResult(MODIFY_PHONE_SUCCESS, intentTemp)
                toast("修改手机号成功")
                tv_owner_phone.text = phone
                dialog.dismiss()
            })
            ownerUpdateData.observerError(this@OwnerDetailActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }

    private fun setFamilyView(family: Family?) {
        if (family != null) {
            val str = StringBuilder()
            for (i in family.members) {
                str.append(i.memberName + "、")
            }
            str.deleteCharAt(str.length - 1)
            tv_family.text = str
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setHouseView(house: House?) {
        if (house != null) {
            tv_house.text =
                house.houseType + "-" + house.building + "号楼-" + house.floor + "层-" + house.room
        }
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showDialog() {
        dialog = Dialog(this, R.style.DialogTheme)
        val view: View = LayoutInflater.from(this).inflate(R.layout.modefy_phone_dialog, null)

        val etPhone = view.findViewById<EditText>(R.id.et_phone)

        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        etPhone.hint = owner.phone
        btnConfirm.setOnClickListener {
            phone = etPhone.text.toString()
            logd("phone = $phone")
            if (TextUtils.isEmpty(phone)) {
                toast("手机号不能为空")
                return@setOnClickListener
            }
            if (phone.length < 11) {
                toast("请输入正确的手机号")
                return@setOnClickListener
            }
            viewModel.updateOwner(owner.ownerId, owner.name, phone, owner.houseId, owner.familyId)
        }

        btnCancel.setOnClickListener { dialog.dismiss() }

        dialog.setContentView(view)
        val layoutParams: ViewGroup.MarginLayoutParams =
            view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.width = resources.displayMetrics.widthPixels
        view.layoutParams = layoutParams
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.show()
    }
}