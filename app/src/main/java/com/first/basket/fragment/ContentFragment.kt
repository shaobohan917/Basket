package com.first.basket.fragment

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.MainActivity
import com.first.basket.adapter.ContentAdapter
import com.first.basket.adapter.SecondAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.HotRecommendBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_content.*
import java.util.*

@SuppressLint("ValidFragment")
/**
 * Created by hanshaobo on 17/09/2017.
 */
class ContentFragment(activity: MainActivity,data: ClassifyBean.DataBean) : BaseFragment() {
    private var activity = activity
    private var mDatas = data

    private lateinit var mContentAdapter: ContentAdapter
    private lateinit var mSecondAdapter: SecondAdapter

    private var mContentDatas = ArrayList<ProductBean>()
    private var mSecondDatas = ArrayList<ClassifyBean.DataBean.LeveltwoBean>()

    private lateinit var contentRecyclerView: RecyclerView
    private lateinit var secondRecyclerView: RecyclerView

    //购物车
    private lateinit var mPathMeasure: PathMeasure
    private val mCurrentPosition = FloatArray(2)
    private var mCount = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentRecyclerView = view.findViewById(R.id.contentRecyclerView)
        secondRecyclerView = view.findViewById(R.id.secondRecyclerView)

        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        contentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                } else if (dy < 0) {
                }
            }
        })

    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
        secondRecyclerView.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun initData() {
        //初始化商品列表adapter
        mContentAdapter = ContentAdapter(activity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

        mSecondAdapter = SecondAdapter(activity, mSecondDatas)
        mSecondAdapter.setOnItemClickListener { view, data, position ->
            var id = data.leveltwoid
            var twoidSb = StringBuilder()
            if ("000".equals(id)) {
                for (i in 0 until mDatas.leveltwo.size) {
                    twoidSb.append(mDatas.leveltwo[i].leveltwoid).append(",")
                }
                var str = twoidSb.toString()
                str = str.substring(0, twoidSb.length - 1)
                getProduct(str)
            } else {
                getProduct(id)
            }

        }
        secondRecyclerView.adapter = mSecondAdapter

        mContentAdapter.setOnItemClickListener { view, data, position ->
            var intent = Intent(activity, GoodsDetailActivity::class.java)
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
            (activity as MainActivity).setCountAdd()
        }
    }


    /**
     * 将商品添加到购物车
     */
    private fun addGoodToCar(imageView: ImageView) {

        var view = ImageView(activity)
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
                val scaleAnim = AnimationUtils.loadAnimation(activity, R.anim.shop_car_scale)
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


    /**
     * 获取商品列表
     */
    private fun getProduct(leveltwoId: String) {
        HttpMethods.createService().getProducts("get_products", activity.mChannel.toString(), leveltwoId)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<ClassifyContentBean>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(t: ClassifyContentBean) {
                        mContentDatas.clear()
                        val dataBean = t.result.data

                        for (i in 0 until dataBean.size) {
                            mContentDatas.add(dataBean[i])
                        }
                        mContentAdapter.notifyDataSetChanged()
                        activity.hideProgress()
                    }
                })
    }

    fun setContentData(position: Int, dataBean: ClassifyBean.DataBean) {
        if (position != 0) {

            var twoidSb = StringBuilder()
            for (i in 0 until mDatas.leveltwo.size) {
                twoidSb.append(mDatas.leveltwo[i].leveltwoid).append(",")
            }
            var str = twoidSb.toString()
            str = str.substring(0, twoidSb.length - 1)
            getProduct(str)

            var leveltwobean = ClassifyBean.DataBean.LeveltwoBean()
            leveltwobean.leveltwodesc = "全部"
            leveltwobean.leveltwoid = "000"
            mSecondDatas.add(leveltwobean)
            mSecondDatas.addAll(dataBean.leveltwo)
            mSecondAdapter.notifyDataSetChanged()

            //默认选中第一个
//            secondRecyclerView.getLayoutManager().smoothScrollToPosition(secondRecyclerView, null, mSecondAdapter.getItemCount() - 1)
        } else {

        }
    }
}