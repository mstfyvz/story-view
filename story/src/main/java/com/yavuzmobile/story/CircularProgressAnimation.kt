package com.yavuzmobile.story

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin

class CircularProgressAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val dottedPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val numberOfPoints = 20
    private val pointRadius = 5f
    private val circleRadius = 100f
    private var startAngle = -90f
    private val sweepAngle = 360f / numberOfPoints
    private var currentPointIndex = 0

    private val colors = intArrayOf(
        ContextCompat.getColor(context, R.color.instagramRoyalBlue),
        ContextCompat.getColor(context, R.color.instagramBlue),
        ContextCompat.getColor(context, R.color.instagramPurple),
        ContextCompat.getColor(context, R.color.instagramDarkPink),
        ContextCompat.getColor(context, R.color.instagramPurpleRed),
        ContextCompat.getColor(context, R.color.instagramRed),
        ContextCompat.getColor(context, R.color.instagramDarkOrange),
        ContextCompat.getColor(context, R.color.instagramOrange),
        ContextCompat.getColor(context, R.color.instagramYellow),
        ContextCompat.getColor(context, R.color.instagramLightYellow),
        // Diğer renkleri buraya ekleyebilirsiniz.
    )

    init {
        dottedPaint.style = Paint.Style.FILL
        startAnimations()
    }

    private fun startDottedAnimation() {
        val animator = ValueAnimator.ofInt(0, numberOfPoints)
        animator.repeatCount = 0
        animator.interpolator = LinearInterpolator()
        animator.duration = 3000 // Animasyon süresini ayarlayabilirsiniz.

        animator.addUpdateListener { valueAnimator ->
            currentPointIndex = valueAnimator.animatedValue as Int
            startAngle = -90f + currentPointIndex * sweepAngle
            invalidate()
        }

        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredSize = 100.dpToPx()
        val width = resolveSize(desiredSize, widthMeasureSpec)
        val height = resolveSize(desiredSize, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        drawDotted(canvas)
    }

    private fun drawDotted(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        for (i in 0 until numberOfPoints) {
            val angle = startAngle + i * sweepAngle
            val x = centerX + circleRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = centerY + circleRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            dottedPaint.color = colors[i % colors.size] // Renkleri dönüş sırasına göre belirle

            canvas.drawCircle(x, y, pointRadius, dottedPaint)
        }
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

    // Animasyonları başlatmak için bir metodunuzu çağırabilirsiniz.
    fun startAnimations() {
        startDottedAnimation()
    }
}
