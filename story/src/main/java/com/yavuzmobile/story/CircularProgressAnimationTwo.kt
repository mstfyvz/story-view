package com.yavuzmobile.story

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircularProgressAnimationTwo(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val ARC_FULL_ROTATION_DEGREE: Int = 360
        private const val PERCENTAGE_DIVIDER: Double = 100.0
        private const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

    private var currentPercentage = 0
    private val ovalSpace = RectF()

    private val fillArcColor: Int = ContextCompat.getColor(context, android.R.color.holo_orange_dark)

    private val fillArcPaint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        color = fillArcColor
        strokeWidth = 10f
        strokeCap = Paint.Cap.ROUND
    }

    init {
        animateProgress()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = 100.dpToPx()
        val width = resolveSize(desiredSize, widthMeasureSpec)
        val height = resolveSize(desiredSize, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setSpace()
        drawInnerArc(canvas)
    }

    private fun setSpace() {
        val horizontalCenter = (width.div(2)).toFloat()
        val verticalCenter = (height.div(2)).toFloat()
        val ovalSize = 100
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun drawInnerArc(canvas: Canvas) {
        val percentageToFill = getCurrentPercentageToFill()
        canvas.drawArc(ovalSpace, 270f, percentageToFill, false, fillArcPaint)
    }

    private fun getCurrentPercentageToFill() = (ARC_FULL_ROTATION_DEGREE * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    private fun animateProgress() {
        val valuesHolder = PropertyValuesHolder.ofFloat(PERCENTAGE_VALUE_HOLDER, 0f, 100f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 3000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }
}

