package com.sar.sarandroidfunctions

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * 用于处理VideoView关联MediaController之后在刘海屏上横屏显示时MediaController位置向右偏移的问题
 */
class CustomAnchorViewForVideoView : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun getLocationOnScreen(outLocation: IntArray) {
        super.getLocationInWindow(outLocation)
    }
}