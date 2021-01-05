package com.example.management_system.module.owner

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R
import com.example.management_system.data.Owner
import com.example.management_system.utils.ImageLoader
import com.example.management_system.utils.SpUtil

class OwnerAdapter : BaseQuickAdapter<Owner, BaseViewHolder>(R.layout.rv_item_owner) {
    override fun convert(helper: BaseViewHolder, item: Owner?) {
        helper.run {
            if (item != null) {
                setText(R.id.tv_owner_name, item.name)
                setText(R.id.tv_owner_phone, item.phone)
                ImageLoader.loadLocalIcon(
                    helper.getView(R.id.iv_owner),
                    SpUtil.getInt((item.ownerId % 9 + 1).toString())
                )
            }

            addOnClickListener(R.id.iv_owner)
            addOnClickListener(R.id.tv_owner_name)
        }
    }

    fun setData(it: MutableList<Owner>) {
        mData.clear()
        mData.addAll(it)
        notifyDataSetChanged()
    }

    fun setPhone(position: Int, phone: String?) {
        if (phone != null) {
            mData[position].phone = phone
        }
        notifyDataSetChanged()
    }
}