package com.first.basket.common

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.first.basket.R
import com.first.basket.app.BaseApplication
import com.first.basket.bean.ProductBean
import com.first.basket.utils.SPUtil
import com.first.basket.utils.UIUtils
import com.google.gson.Gson

/**
 * Created by hanshaobo on 26/10/2017.
 */
class CommonMethod1 {
    companion object {
        /**
         * 将商品添加到购物车
         */
        fun addGoodToCar(imageView: ImageView, rlRoot: RelativeLayout, ivCar: ImageView, listener: OnAddListener?) {
            var mPathMeasure: PathMeasure
            val mCurrentPosition = FloatArray(2)

            var view = ImageView(UIUtils.getContext())
            view.setImageDrawable(imageView.drawable)
            var layoutParams = RelativeLayout.LayoutParams(100, 100)

            rlRoot.addView(view, layoutParams)

            val parentLoc = IntArray(2)
            rlRoot.getLocationInWindow(parentLoc)

            var startLoc = IntArray(2)
            imageView.getLocationInWindow(startLoc)

            var endLoc = IntArray(2)
            ivCar.getLocationInWindow(endLoc)


            val startX = startLoc[0] - parentLoc[0] + imageView.width / 2
            val startY = startLoc[1] - parentLoc[1] + imageView.height / 2

            //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
            val toX = endLoc[0] - parentLoc[0] + ivCar.getWidth() / 5
            val toY = endLoc[1] - parentLoc[1]

            //开始绘制贝塞尔曲线
            val path = Path()
            path.moveTo(startX.toFloat(), startY.toFloat())
            //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
            path.quadTo(((startX + toX) / 2).toFloat(), startY.toFloat(), toX.toFloat(), toY.toFloat())
            mPathMeasure = PathMeasure(path, false)

            //属性动画
            val valueAnimator = ValueAnimator.ofFloat(0F, mPathMeasure.getLength())
            valueAnimator.duration = 1000
            valueAnimator.interpolator = LinearInterpolator()

            valueAnimator.addUpdateListener { animation ->
                val value = animation.getAnimatedValue() as Float
                mPathMeasure.getPosTan(value, mCurrentPosition, null)
                view.translationX = mCurrentPosition[0]
                view.translationY = mCurrentPosition[1]
            }

            valueAnimator.addListener(object : Animator.AnimatorListener {

                override fun onAnimationEnd(p0: Animator?) {
                    // 购物车的数量加1
                    listener?.onAdd()
                    // 把移动的图片imageview从父布局里移除
                    rlRoot.removeView(view)
                    //shopImg 开始一个放大动画
                    val scaleAnim = AnimationUtils.loadAnimation(UIUtils.getContext(), R.anim.shop_car_scale)
                    ivCar.startAnimation(scaleAnim)
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }
            })

            valueAnimator.start()
        }

        interface OnAddListener {
            fun onAdd()
        }

        fun saveProduct() {
            val products = BaseApplication.getInstance().productsList
            val gson = Gson()
            val str = gson.toJson(products)
            SPUtil.setString(StaticValue.GOODS_LIST, str)
        }

        fun showPrice(product: ProductBean): String {
            return if (CommonMethod.isTrue(product.promboolean)) {
                product.promdata.promprice
            } else {
                product.price
            }
        }
    }
}