package com.first.basket.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.first.basket.R
import com.first.basket.adapter.ContentAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.ProductBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by hanshaobo on 22/10/2017.
 */
class SearchListActivity : BaseActivity() {

    private var mContentDatas = ArrayList<ProductBean>()

    //购物车
    private lateinit var mPathMeasure: PathMeasure
    private val mCurrentPosition = FloatArray(2)
    private var mCount = 0

    private lateinit var mContentAdapter: ContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        mContentAdapter.setOnItemClickListener { view, data, position ->
            var intent = Intent(this@SearchListActivity, GoodsDetailActivity::class.java)
            intent.putExtra("id", data.productid)
            startActivity(intent)
        }
        mContentAdapter.setOnAddItemClickListener { view, data, position ->
            addGoodToCar(view.findViewById(R.id.ivGoods))
            data.isCheck = true
            data.amount++
            var goodsMap = BaseApplication.getInstance().mGoodsMap
            if (goodsMap.containsKey(data)) {
                goodsMap.put(data, goodsMap.getValue(data) + 1)
            } else {
                goodsMap.put(data, 1)
            }
            BaseApplication.getInstance().mGoodsMap = goodsMap
        }

        ivCar.onClick {

        }

    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(this@SearchListActivity)
        mContentAdapter = ContentAdapter(this@SearchListActivity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

    }

    private fun initData() {
        var search = intent.getStringExtra("search")
        LogUtils.d("search:" + search)
        getProduct(search)
    }


    /**
     * 搜索商品列表
     */
    private fun getProduct(searchString: String) {
        HttpMethods.createService().getProducts("get_products", "", "", searchString, "")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyContentBean>>() {
                    override fun onNext(t: HttpResult<ClassifyContentBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            mContentDatas.clear()
                            val dataBean = t.result.data
                            for (i in 0 until dataBean.size) {
                                mContentDatas.add(dataBean[i])
                            }
                            mContentAdapter.notifyDataSetChanged()
                        } else {
                            ToastUtil.showToast("暂无搜索结果")
                        }
                    }
                })
    }

    /**
     * 将商品添加到购物车
     */
    private fun addGoodToCar(imageView: ImageView) {

        var view = ImageView(this@SearchListActivity)
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
                val scaleAnim = AnimationUtils.loadAnimation(this@SearchListActivity, R.anim.shop_car_scale)
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
}