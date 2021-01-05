package com.example.management_system.module.pay

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R
import com.example.management_system.data.PaymentItem

class PaymentItemAdapter :
    BaseQuickAdapter<PaymentItem, BaseViewHolder>(R.layout.rv_item_payment_item) {
    override fun convert(helper: BaseViewHolder, item: PaymentItem?) {
        helper.run {
            if (item != null) {
                setText(R.id.tv_payment_item_name, item.paymentItem)
                setText(R.id.tv_price, "￥" + item.price.toString() + " / " + item.unit)
            }

            addOnClickListener(R.id.iv_delete)
            addOnClickListener(R.id.tv_payment_item_name)
        }
    }
}