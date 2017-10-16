package com.first.basket.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import butterknife.ButterKnife
import com.first.basket.R
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.constants.Constants
import com.first.basket.db.ProductDao
import com.first.basket.fragment.HomeFragment
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.ToastUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.right_list_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import q.rorbin.badgeview.QBadgeView

/**
 * Created by hanshaobo on 02/09/2017.
 */
class GoodsDetailActivity : BaseActivity() {
    private lateinit var data: GoodsDetailBean.ResultBean.DataBean

    private var images = ArrayList<GoodsDetailBean.ResultBean.DataBean.ImagesBean>()
    private lateinit var badgeView: QBadgeView

    //购物车
    private lateinit var mPathMeasure: PathMeasure
    private val mCurrentPosition = FloatArray(2)
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_detail)
        initView()
        getData(intent.extras.getString("id", ""))
        initListener()
    }

    override fun onResume() {
        super.onResume()
        badgeView.bindTarget(tvCount).badgeNumber = BaseApplication.getInstance().mGoodsMap.size
        LogUtils.d("basecount:" + BaseApplication.getInstance().mGoodsMap.size)


    }

    private fun initView() {
        badgeView = QBadgeView(this@GoodsDetailActivity)
    }

    private fun initListener() {
        btAdd.onClick {
            addGoodToCar(ivGoods)

            tvCount.text = mCount.toString()
            LogUtils.d("count:" + mCount)
            badgeView.bindTarget(tvCount).badgeNumber = mCount

            ProductDao.getInstance(this@GoodsDetailActivity).insertOrUpdateItem(data.product)
        }
        ivCar.onClick {
            intent.putExtra("goods", data)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun getData(id: String) {
        HttpMethods.createService().getDetail("get_productdetailpage", id)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<GoodsDetailBean>() {
                    override fun onNext(t: GoodsDetailBean) {
                        val data = t.result!!.data
                        setData(data!!)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }


    private fun setData(data: GoodsDetailBean.ResultBean.DataBean) {
        this.data = data
        tvName.text = data.title
        tvDes.text = data.subtitle.toString()
        tvPrice.text = getString(R.string.price, data.price.toString())
        tvDetail.text = data.productdetail ?: ""
        //设置banner
        images.addAll(data.images)
        var imgs = ArrayList<String>()
        for (i in 0 until images.size) {
            imgs.add(Constants.BASE_IMG_URL + images[i].image)
        }
        banner.setImages(imgs)
                .setImageLoader(HomeFragment.GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

        ImageUtils.showImg(this@GoodsDetailActivity, data.images.get(0).image, ivGoods)
    }


    /**
     * 将商品添加到购物车
     */
    private fun addGoodToCar(imageView: ImageView) {

        var view = ImageView(this@GoodsDetailActivity)
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
                mCount++
//                mCountTv.setText(String.valueOf(mCount))
                // 把移动的图片imageview从父布局里移除
                rlRoot.removeView(view)

                //shopImg 开始一个放大动画
                val scaleAnim = AnimationUtils.loadAnimation(this@GoodsDetailActivity, R.anim.shop_car_scale)
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

    override fun onStop() {
        super.onStop()

        //加入购物车
//        BaseApplication.getInstance().mGoodsMap.put(data.product, mCount)
    }
}