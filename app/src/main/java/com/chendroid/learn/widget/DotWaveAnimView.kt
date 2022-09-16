package com.chendroid.learn.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @author      : zhaochen
 * @date        : 2021/11/5
 * @description :
 */
class DotWaveAnimView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

//    // region 静态常量
//    companion object {
//        /**
//         * 动画时间为 1000
//         */
//        const val ANIM_DURATION = 1000L
//
//        // 三个动画间隔的时间
//        const val ANIM_DELAY_TIME = 333L
//    }
//    // endregion
//
//    /**
//     * MatchingDotWaveAnimView 的宽和高
//     */
//    private var viewHeight = 0
//    private var viewWidth = 0
//
//    /**
//     * 圆点的半径；暂定为确定的值: 3dp； 或者 宽的 1/8「计算出来的」
//     */
//    private var dotRadius = Metrics.dp(3F).toFloat()
//
//    /**
//     * 圆点之间的距离：3dp
//     */
//    private var dotDistance = Metrics.dp(3F).toFloat()
//
//    private val paint: Paint = Paint()
//
//    /**
//     * 第一个圆点的中心 Y 坐标
//     */
//    private var firstCenterY = dotRadius * 4
//
//    /**
//     * 第二个圆点的中心 Y 坐标
//     */
//    private var secondCenterY = dotRadius * 4
//
//    /**
//     * 第三个圆点的中心 Y 坐标
//     */
//    private var thirdCenterY = dotRadius * 4
//
//    private var animSet: AnimatorSet? = null
//
//    init {
//        LogUtils.d("zc_test", "点波浪动画 init()")
//        paint.apply {
//            style = Paint.Style.FILL
//            isAntiAlias = true
//            setColor(getContext().resources.getColor(R.color.live_fe7e1d))
//            strokeWidth = dotRadius
//            alpha = (0.3 * 255).toInt()
//        }
//    }
//
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        super.onSizeChanged(w, h, oldw, oldh)
//        viewHeight = h
//        viewWidth = w
//    }
//
//
//    /**
//     * 开启点做波浪动画
//     */
//    fun startDotWaveAnim() {
//        val firstDotAnim = ValueAnimator.ofFloat(dotRadius * 4, dotRadius * 2, dotRadius * 4).apply {
//            duration = ANIM_DURATION
//            repeatCount = ValueAnimator.INFINITE
//        }
//
//        firstDotAnim.addUpdateListener {
//            firstCenterY = it.animatedValue as Float
//            postInvalidate()
//        }
//
//        val secondDotAnim = ValueAnimator.ofFloat(dotRadius * 4, dotRadius * 2, dotRadius * 4).apply {
//            duration = ANIM_DURATION
//            startDelay = ANIM_DELAY_TIME
//            repeatCount = ValueAnimator.INFINITE
//        }
//        secondDotAnim.addUpdateListener {
//            secondCenterY = it.animatedValue as Float
//        }
//
//        val thirdDotAnim = ValueAnimator.ofFloat(dotRadius * 4, dotRadius * 2, dotRadius * 4).apply {
//            duration = ANIM_DURATION
//            startDelay = ANIM_DELAY_TIME * 2
//            repeatCount = ValueAnimator.INFINITE
//        }
//
//        thirdDotAnim.addUpdateListener {
//            thirdCenterY = it.animatedValue as Float
//        }
//
//        if (animSet == null) {
//            animSet = AnimatorSet().apply {
//                playTogether(firstDotAnim, secondDotAnim, thirdDotAnim)
//            }
//            animSet?.start()
//        }
//    }
//
//    fun clearAnim() {
//        AnuHelper.cancelAnimAndRemoveListeners(animSet)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        //  待确定第二个第三个点的实现
////    val percent = ((System.currentTimeMillis() % ANIM_DURATION)/ ANIM_DURATION).toFloat()
//        canvas.run {
//            drawCircle(dotRadius, firstCenterY, dotRadius, paint)
//            drawCircle(dotRadius * 3 + dotDistance, secondCenterY, dotRadius, paint)
//            drawCircle(dotRadius * 5 + dotDistance * 2, thirdCenterY, dotRadius, paint)
//        }
//    }


}