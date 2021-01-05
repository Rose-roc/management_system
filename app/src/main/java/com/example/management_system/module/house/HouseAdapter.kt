package com.example.management_system.module.house

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R
import com.example.management_system.data.House
import com.example.management_system.utils.ImageLoader

class HouseAdapter : BaseQuickAdapter<House, BaseViewHolder>(R.layout.rv_item_house) {
    override fun convert(helper: BaseViewHolder, item: House?) {
        helper.run {
            if (item != null) {
                setText(R.id.tv_houseType, item.houseType)
                setText(
                    R.id.tv_position,
                    "位于 " + item.building + "号楼 " + item.floor + "层 " + item.room
                )
                setText(R.id.tv_area, "面积：" + item.area + " /m²")

                if (item.message != null) {
                    setVisible(R.id.tv_house_message, true)
                    setText(R.id.tv_house_message, "备注：" + item.message)
                }
                ImageLoader.loadLocalIcon(
                    helper.getView(R.id.iv_house),
                    when (item.houseType) {
                        "公寓" -> R.drawable.high_level_house
                        "高档住宅" -> R.drawable.apartment
                        else -> R.drawable.normal_house
                    }
                )
            }
//            addOnClickListener(R.id.iv_house)
        }
    }

    fun setData(it: MutableList<House>) {
        mData.clear()
        mData.addAll(it)
        notifyDataSetChanged()
    }
}