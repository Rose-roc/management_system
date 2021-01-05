package com.example.management_system.module.house

import androidx.appcompat.app.AppCompatActivity
import com.example.management_system.R
import com.example.management_system.data.HouseType
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.gyf.immersionbar.ImmersionBar
import com.zhan.ktwing.ext.Toasts.toast
import com.zhan.mvvm.annotation.BindViewModel
import com.zhan.mvvm.mvvm.IMvmActivity
import kotlinx.android.synthetic.main.activity_housing_statictics.*
import kotlinx.android.synthetic.main.title_bar.*

class HousingStatisticsActivity : AppCompatActivity(), IMvmActivity {
    override fun getLayoutId(): Int {
        return R.layout.activity_housing_statictics
    }

    @BindViewModel
    lateinit var viewModel: HousingStatisticsViewModel

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).titleBar(title_bar).init()
        title_bar_text.text = "房屋类型统计信息"

        viewModel.getAllHouseType()
    }

    override fun initListener() {
        super.initListener()
        title_bar_return.setOnClickListener {
            finish()
        }
    }

    override fun dataObserver() {
        super.dataObserver()
        viewModel.run {
            houseTypeListData.observe(this@HousingStatisticsActivity, { initBar(it) })
            houseTypeListData.observerError(this@HousingStatisticsActivity, {
                toast(it?.message.toString())
            }, {
                it?.let { it1 -> toast(it1) }
            })
        }
    }

    private fun initBar(houseTypeList: List<HouseType>) {
        var max = 0
        val list: MutableList<BarEntry> = mutableListOf()

        for (i in houseTypeList.indices) {
            max = max.coerceAtLeast(houseTypeList[i].count)
            list.add(BarEntry((i + 1).toFloat(), houseTypeList[i].count.toFloat()))
        }

        val barDataSet = BarDataSet(list, "房屋类型") //list是你这条线的数据  "房屋类型" 是你对这条线的描述
        val barData = BarData(barDataSet)
        bar.data = barData

        bar.description.isEnabled = false                 //是否显示右下角描述

        //折线图背景
        bar.xAxis.setDrawGridLines(false) //是否绘制X轴上的网格线（背景里面的竖线）
        bar.axisLeft.setDrawGridLines(false) //是否绘制Y轴上的网格线（背景里面的横线）

        //X轴
        val xAxis: XAxis = bar.xAxis
        xAxis.setDrawGridLines(false) //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.axisLineWidth = 1f //X轴粗细
        xAxis.position = XAxis.XAxisPosition.BOTTOM //X轴所在位置   默认为上面
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return houseTypeList.getOrNull(value.toInt() - 1)?.houseType ?: value.toString()
            }
        }
        xAxis.axisMaximum = (houseTypeList.size + 1).toFloat() //X轴最大数值
        xAxis.axisMinimum = 0f //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        xAxis.setLabelCount(houseTypeList.size, false)

        //Y轴
        val axisLeft: YAxis = bar.axisLeft
        axisLeft.setDrawGridLines(false) //是否绘制Y轴上的网格线（背景里面的横线）
        axisLeft.axisLineWidth = 1f //Y轴粗细


        axisLeft.axisMaximum = (max + 5).toFloat() //Y轴最大数值
        axisLeft.axisMinimum = 0f //Y轴最小数值
        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        axisLeft.setLabelCount(max + 5, false)
        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
        bar.axisRight.isEnabled = false

        //柱子
        barDataSet.setColors(
            ColorTemplate.rgb("#2ecc71"),
            ColorTemplate.rgb("#e74c3c"),
            ColorTemplate.rgb("#3498db")
        ) //设置柱子多种颜色  循环使用

        //动画（如果使用了动画可以则省去更新数据的那一步）
        bar.animateXY(1000, 2000);//XY两轴混合动画

        bar.setScaleEnabled(false)
    }
}
