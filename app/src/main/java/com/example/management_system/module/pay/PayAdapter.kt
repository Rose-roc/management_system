package com.example.management_system.module.pay

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R
import com.example.management_system.data.Pay

class PayAdapter : BaseQuickAdapter<Pay, BaseViewHolder>(R.layout.rv_item_pay) {
    override fun convert(helper: BaseViewHolder, item: Pay?) {
        helper.run {
            if (item != null) {
                setText(R.id.tv_payment_item_name, item.paymentItem)
                setText(R.id.tv_owner_name, "业主id：" + item.ownerId)
                //判空
                if(item.date != "null") setText(R.id.tv_time, item.date)
                setText(R.id.tv_usage_amount, "使用量：" + item.usageAmount)
                //更改颜色，未付则为红色，已付为绿色
                setText(R.id.tv_total_cost, "￥" + item.totalCost)
                if(item.payStatus == 1) {
                    val view: TextView = getView(R.id.tv_total_cost)
                    view.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                }
            }

            addOnClickListener(R.id.iv_delete)
            addOnClickListener(R.id.tv_payment_item_name)
        }
    }
}
