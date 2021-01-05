package com.example.management_system.module.pay

import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.management_system.R

class PaymentItemDialog(context: Context?) : AlertDialog(context), View.OnClickListener {

    var call: ClickCallBack? = null
    var etName: EditText? = null
    var etPrice: EditText? = null
    var etUnit: EditText? = null
    var yesButton: Button? = null
    var noButton: Button? = null

    constructor(context: Context?, yesCallBack: ClickCallBack) : this(
        context,
        "",
        "",
        "",
        yesCallBack,
        true
    )

    constructor(
        context: Context?,
        name: String,
        price: String,
        unit: String,
        yesCallBack: ClickCallBack,
        isEdit: Boolean
    ) : this(context) {
        call = yesCallBack
        etName?.hint = name
        etPrice?.hint = price
        etUnit?.hint = unit
        if(!isEdit){
            //去掉点击时编辑框下面横线:
            etName?.isEnabled = false
            //不可编辑
            etName?.isFocusable = false
            //失去焦点
            etName?.isFocusableInTouchMode = false
        }
    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_payment_item, null)
        setView(inflate)

        etName = inflate.findViewById(R.id.et_name)
        etPrice = inflate.findViewById(R.id.et_price)
        etUnit = inflate.findViewById(R.id.et_unit)
        yesButton = inflate.findViewById(R.id.btn_confirm)
        noButton = inflate.findViewById(R.id.btn_cancel)

        yesButton?.setOnClickListener(this)
        noButton?.setOnClickListener { dismiss() }

        val w = this.window
        val lp = w!!.attributes
        lp.x = 0
        lp.y = -100
    }


    override fun onClick(p0: View?) {
        call?.yesClick(this);
    }

    interface ClickCallBack {
        fun yesClick(dialog: PaymentItemDialog)
    }

}