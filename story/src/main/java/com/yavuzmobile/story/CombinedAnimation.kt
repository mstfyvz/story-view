package com.yavuzmobile.story

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CheckResult
import androidx.core.content.ContextCompat
import kotlin.math.cos
import kotlin.math.sin

class CombinedAnimation(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val dottedPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val numberOfPoints = 20
    private val pointRadius = 5f
    private var startAngle = -90f
    private val sweepAngle = 360f / numberOfPoints
    private var currentPointIndex = 0
    private val desiredSize = 100.dpToPx()
    private var width: Int = 0
    private var height: Int = 0
    private var currentPercentage = 0

    var isOpened: Boolean = false
        @CheckResult
        get
        set(value) {
            field = value
            invalidate()
        }

    private val ovalSpace = RectF()

    private var colors: IntArray

    private val fillArcPaint = Paint()

    private val backgroundPaint = Paint()

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.StoryView, 0, 0)
        isOpened = typedArray.getBoolean(R.styleable.StoryView_isOpened, false)

        colors = intArrayOf(
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
        )
        backgroundPaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 10f
            strokeCap = Paint.Cap.ROUND
        }
        fillArcPaint.apply {
                style = Paint.Style.STROKE
                isAntiAlias = true
                strokeWidth = 10f
                strokeCap = Paint.Cap.ROUND
            }
    }

    fun startAnimations() {
        startDottedAnimation()
        startArcAnimation()
    }

    private fun startDottedAnimation() {
        val animator = ValueAnimator.ofInt(0, numberOfPoints)
        animator.repeatCount = 0
        animator.duration = 3000
        animator.addUpdateListener { valueAnimator ->
            currentPointIndex = valueAnimator.animatedValue as Int
            startAngle = -90f + currentPointIndex * sweepAngle
            invalidate()
        }
        animator.start()
    }

    private fun startArcAnimation() {
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, 100f)
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            duration = 3000
            addUpdateListener {
                val percentage = it.getAnimatedValue("percentage") as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        width = resolveSize(desiredSize, widthMeasureSpec)
        height = resolveSize(desiredSize, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        if (isOpened) {
            drawBackground(canvas)
        } else {
            drawDotted(canvas)
            drawInnerArc(canvas)
        }
    }

    private fun drawDotted(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f

        for (i in 0 until numberOfPoints) {
            val angle = startAngle + i * sweepAngle
            val x = centerX + ((width - 10) / 2) * cos(Math.toRadians(angle.toDouble())).toFloat()
            val y = centerY + ((height - 10) / 2) * sin(Math.toRadians(angle.toDouble())).toFloat()

            dottedPaint.color = colors[i % colors.size]
            canvas.drawCircle(x, y, pointRadius, dottedPaint)
        }
    }

    private fun drawInnerArc(canvas: Canvas) {
        setSpace()

        val percentageToFill = (360 * (currentPercentage / 100.0)).toFloat()

        // create gradient color
        val gradientColors = IntArray(colors.size)
        colors.indices.forEach {
            gradientColors[it] = colors[it]
        }

        val gradient = SweepGradient(ovalSpace.centerX(), ovalSpace.centerY(), gradientColors, null)

        fillArcPaint.shader = gradient

        canvas.drawArc(ovalSpace, -90f, percentageToFill, false, fillArcPaint)
    }

    private fun drawBackground(canvas: Canvas) {
        setSpace()

        backgroundPaint.color = ContextCompat.getColor(context, R.color.gray)

        canvas.drawCircle(width / 2.0f, height / 2.0f, (width - 10) / 2.0f, backgroundPaint)
    }

    private fun setSpace() {
        val horizontalCenter = (width / 2).toFloat()
        val verticalCenter = (height / 2).toFloat()
        val ovalSize = (width - 10) / 2
        ovalSpace.set(
            horizontalCenter - ovalSize,
            verticalCenter - ovalSize,
            horizontalCenter + ovalSize,
            verticalCenter + ovalSize
        )
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density).toInt()
    }

}