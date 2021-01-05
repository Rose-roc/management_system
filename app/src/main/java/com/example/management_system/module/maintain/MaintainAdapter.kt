package com.example.management_system.module.maintain

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R
import com.example.management_system.data.Maintain

class MaintainAdapter : BaseQuickAdapter<Maintain, BaseViewHolder>(R.layout.rv_item_maintain) {
    override fun convert(helper: BaseViewHolder, item: Maintain?) {
        helper.run {
            if (item != null) {
                setText(R.id.tv_maintain_id, item.maintainId.toString())
                setText(R.id.tv_owner_name, item.content)
                setText(R.id.tv_registration_time, item.registrationTime)
                //判空
                if (item.cost != null && item.cost != "null") {
                    setVisible(R.id.tv_maintain_cost, true)
                    setText(
                        R.id.tv_maintain_cost,
                        "￥" + item.cost.toString()
                    )
                    setBackgroundRes(R.id.view_point,R.drawable.bg_point_green)
                }
                if (item.finishedTime != null && item.finishedTime != "null") {
                    setVisible(R.id.tv_maintain_finished_time, true)
                    setText(
                        R.id.tv_maintain_finished_time,
                        " 于" + item.finishedTime.toString() + " 完成维修"
                    )
                    setBackgroundRes(R.id.view_point,R.drawable.bg_point_green)
                }
            }
//            addOnClickListener(R.id.iv_delete)
//            addOnClickListener(R.id.tv_payment_item_name)
        }
    }
}