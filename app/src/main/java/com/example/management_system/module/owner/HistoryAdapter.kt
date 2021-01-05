package com.example.management_system.module.owner

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.management_system.R

class HistoryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_search_history) {

    private var mRemoveModeChanging = false
    private var mRemoveMode = false

    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.run {
            setText(R.id.tv_key, item)
            addOnClickListener(R.id.iv_remove)
            val ivRemove = getView<ImageView>(R.id.iv_remove)
            if (!mRemoveModeChanging) {
                helper.setVisible(R.id.iv_remove, mRemoveMode)
            } else {
                //如果要显示每一项清除的按钮
                if (mRemoveMode) {
                    //缩放动画，逐渐显示
                    val scaleAnimation: ScaleAnimation = getScaleAnimationByData(0f, 1f, 0f, 1f)
                    scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {
                            ivRemove.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animation) {
                            mRemoveModeChanging = false
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                    ivRemove.startAnimation(scaleAnimation)
                } else {
                    //如果隐藏清除的按钮
                    val scaleAnimation: ScaleAnimation = getScaleAnimationByData(1f, 0f, 1f, 0f)
                    scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(animation: Animation) {}
                        override fun onAnimationEnd(animation: Animation) {
                            mRemoveModeChanging = false
                            ivRemove.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                    ivRemove.startAnimation(scaleAnimation)
                }
            }
        }
    }

    private fun getScaleAnimationByData(
        fromX: Float,
        toX: Float,
        fromY: Float,
        toY: Float
    ): ScaleAnimation {
        val scaleAnimation = ScaleAnimation(
            fromX, toX, fromY, toY,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 300
        return scaleAnimation
    }

    fun setRemoveModeChanging(isChanging: Boolean) {
        mRemoveModeChanging = isChanging
    }

    fun setRemoveMode(isRemove: Boolean) {
        mRemoveMode = isRemove
    }
}