package com.first.basket.fragment

import android.graphics.PathMeasure
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.view.AmountView
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_recycler_category.view.*
import kotlinx.android.synthetic.main.item_recycler_content.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.ArrayList

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragmentBack : BaseFragment() {
    private lateinit var mCategoryAdapter: BaseRecyclerAdapter<ClassifyBean.DataBean, BaseRecyclerAdapter.ViewHolder<ClassifyBean.DataBean>>
    private lateinit var mContentAdapter: BaseRecyclerAdapter<ClassifyContentBean.ResultBean.DataBean, BaseRecyclerAdapter.ViewHolder<ClassifyContentBean.ResultBean.DataBean>>

    private var mCategoryDatas = ArrayList<ClassifyBean.DataBean>()
    private var mContentDatas = ArrayList<ClassifyContentBean.ResultBean.DataBean>()
    //存储商品分类id
    private var mIds = ArrayList<String>()
    //当前选中的分类
    private var mClassifyPosition = 0


    private lateinit var path: PathMeasure
    // 贝塞尔曲线中间过程点坐标
    private val mCurrentPosition = FloatArray(2)
    // 购物车商品数目
    private var goodsCount = 0

    var baseFragment = BaseFragment()
    var fragmentList = ArrayList<ContentFragment>()


    /**
     * 添加商品到购物车
     * @author leibing
     * @createTime 2016/09/28
     * @lastModify 2016/09/28
     * @param goodsImg 商品图标
     * @return
     */
    //    private fun addGoodsToCart(goodsImg: ImageView) {
//        // 创造出执行动画的主题goodsImg（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
//        var goods = ImageView(activity)
//        goods.setImageDrawable(goodsImg.drawable)
//        val params = RelativeLayout.LayoutParams(100, 100)
//        mShoppingCartRly.addView(goods, params)
//
//        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
//        val parentLocation = IntArray(2)
//        mShoppingCartRly.getLocationInWindow(parentLocation)
//
//        // 得到商品图片的坐标（用于计算动画开始的坐标）
//        val startLoc = IntArray(2)
//        goodsImg.getLocationInWindow(startLoc)
//
//        // 得到购物车图片的坐标(用于计算动画结束后的坐标)
//        val endLoc = IntArray(2)
//        mShoppingCartIv.getLocationInWindow(endLoc)
//
//        // 开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
//        val startX = (startLoc[0] - parentLocation[0] + goodsImg.width / 2).toFloat()
//        val startY = (startLoc[1] - parentLocation[1] + goodsImg.height / 2).toFloat()
//        LogUtils.d("起始点：x:"+startX+",y:"+startY)
//
//        // 商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
//        val toX = endLoc[0] - parentLocation[0] + mShoppingCartIv.getWidth() / 5
//        val toY = (endLoc[1] - parentLocation[1]).toFloat()
//        LogUtils.d("终点：x:"+toX+",y:"+toY)
//
//        // 开始绘制贝塞尔曲线
//        val path = Path()
//        // 移动到起始点（贝塞尔曲线的起点）
//        path.moveTo(startX, startY)
//        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
//        path.quadTo((startX + toX) / 2, startY, toX.toFloat(), toY)
//        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
//        var mPathMeasure = PathMeasure(path, false)
//
//        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
//        val valueAnimator = ValueAnimator.ofFloat(0F, mPathMeasure.getLength())
//        valueAnimator.duration = 500
//
//        // 匀速线性插值器
//        valueAnimator.interpolator = LinearInterpolator()
//        valueAnimator.addUpdateListener { animation ->
//            // 当插值计算进行时，获取中间的每个值，
//            // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
//            val value = animation.animatedValue as Float
//            // 获取当前点坐标封装到mCurrentPosition
//            // boolean getPosTan(float distance, float[] pos, float[] tan) ：
//            // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
//            // mCurrentPosition此时就是中间距离点的坐标值
//            mPathMeasure.getPosTan(value, mCurrentPosition, null)
//            // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
//            goods.translationX = mCurrentPosition[0]
//            goods.translationY = mCurrentPosition[1]
//        }
//
//        // 开始执行动画
//        valueAnimator.start()
//
//        // 动画结束后的处理
//        valueAnimator.addListener(object : Animator.AnimatorListener {
//            override fun onAnimationRepeat(p0: Animator?) {
//            }
//
//            override fun onAnimationCancel(p0: Animator?) {
//            }
//
//            override fun onAnimationStart(p0: Animator?) {
//            }
//
//            override fun onAnimationEnd(p0: Animator?) {
//                // 购物车商品数量加1
//                goodsCount++
////                isShowCartGoodsCount()
////                mShoppingCartCountTv.setText(String.valueOf(goodsCount))
//                // 把执行动画的商品图片从父布局中移除
//                mShoppingCartRly.removeView(goods)
//            }
//        })
//    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_classify, container, false)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
//        initListener()
    }

    private fun initView() {
        val drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        etSearch.setCompoundDrawables(drawable, null, null, null)

        categoryRecyclerView.layoutManager = LinearLayoutManager(activity)
//        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun initData() {
        //初始化分类列表adapter
        mCategoryAdapter = BaseRecyclerAdapter(R.layout.item_recycler_category, mCategoryDatas) { view: View, item: ClassifyBean.DataBean ->
            view.tvCategory.text = item.levelonedesc
            view.onClick {
                var index = categoryRecyclerView.getChildAdapterPosition(view)
                if (mClassifyPosition != index) {
                    setSelected(index)
                    //更新内容页
                    initContent(categoryRecyclerView.getChildAdapterPosition(view))
                    getProduct(item.leveloneid!!)
                }


                //有多少分类，创建多少fragment
                for (i in 0 until 3) {
                    var fragment = ContentFragment()
                    fragmentList.add(fragment)
                }

                initData1(index)
            }
        }
        categoryRecyclerView.adapter = mCategoryAdapter

        //初始化商品列表adapter
        mContentAdapter = BaseRecyclerAdapter(R.layout.item_recycler_content, mContentDatas) { view: View, bean: ClassifyContentBean.ResultBean.DataBean ->
            LogUtils.d("adapter")
            view.tvName.text = bean.productname
            view.tvUnit.text = bean.unit
            view.tvPrice.text = bean.price

            view.amoutView.setGoods_storage(10)

//            var iv = view.findViewById<ImageView>(R.id.ivGoods)
//            iv.setImageResource(R.mipmap.ic_shop_add)
            view.amoutView.setOnAmountChangeListener(object : AmountView.OnAmountChangeListener {
                override fun onAmountChange(view: View?, amount: Int) {


                }

            })

            view.ivGoods.onClick {
                //                addGoodsToCart(ivGoods)
            }
        }
//        contentRecyclerView.adapter = mContentAdapter

        getClassify()
    }

    private fun getClassify() {
        //获取商品分类
        HttpMethods.createService().getClassify("get_productclassification")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyBean>>() {
                    override fun onNext(t: HttpResult<ClassifyBean>) {
                        super.onNext(t)
                        LogUtils.d("next:" + t.result.data.get(0).levelonedesc)
                        LogUtils.d("onNext:" + t.result.data.size)
                        for (i in 0 until t.result.data.size) {
                            mCategoryDatas.add(t.result.data[i])
                            mIds.add(t.result.data[i].leveloneid)
                        }
                        mCategoryAdapter.notifyDataSetChanged()

                        //选中第一个分类
                        Handler().postDelayed(Runnable {
                            setSelected(0)
                            categoryRecyclerView.getChildAt(0).performClick()
                        }, 500)


                        //获取第一条类目内容
                        getProduct(mCategoryDatas[0].leveloneid)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.d("onError:" + e.message)
                    }

                    override fun onCompleted() {

                    }
                })
    }


//    private fun initListener() {
//        contentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//            }
//
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (isSlideToBottom()) {
//                    if (++mClassifyPosition < mIds.size) {
//                        LogUtils.d("获取第" + mClassifyPosition + "个分类数据")
//                        getProduct(mIds[mClassifyPosition])
//                        setSelected(mClassifyPosition)
//                    }
//                }
//            }
//        })
//    }

    /**
     * 判断滑到底部
     */
    //    private fun isSlideToBottom(): Boolean {
//        if (contentRecyclerView.computeVerticalScrollExtent() + contentRecyclerView.computeVerticalScrollOffset()
//                >= contentRecyclerView.computeVerticalScrollRange())
//            return true
//        return false
//    }

    /**
     * 设置选中，其他全部未选中
     */
    private fun setSelected(index: Int) {
        LogUtils.d("选中第：" + index + "个")
        var count = categoryRecyclerView.childCount
        for (i in 0 until count) {
            val view = categoryRecyclerView.getChildAt(i)
            val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
            tvCategory.setBackgroundColor(activity.resources.getColor(R.color.text_bg))
            tvCategory.setCompoundDrawables(null, null, null, null)
        }
        val drawable = activity.resources.getDrawable(R.drawable.category_line)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val view = categoryRecyclerView.getChildAt(index)
        view.tvCategory.setCompoundDrawables(drawable, null, null, null)
        view.tvCategory.setBackgroundColor(activity.resources.getColor(R.color.white))
    }

    private fun initContent(childAdapterPosition: Int) {


    }

    /**
     * 获取商品列表
     */
    fun getProduct(id: String) {
        HttpMethods.createService().getProducts("get_products", id)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<ClassifyContentBean>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(t: ClassifyContentBean) {
                        val dataBean = t.result.data
                        for (i in 0 until 10) {
//                        for (mClassifyPosition in 0 until dataBean.size) {
                            mContentDatas.add(dataBean[i])
                        }
                        mContentAdapter.notifyDataSetChanged()

                        //fragment设置数据
                        LogUtils.d("设置数据")
                    }
                })
    }


    private fun initData1(l: Int) {
        var fragment = BaseFragment()
        when (l) {
            0
            -> fragment = fragmentList.get(0)
            1
            -> fragment = fragmentList.get(1)
            2
            -> fragment = fragmentList.get(2)
            3
            -> fragment = fragmentList.get(3)
            4
            -> fragment = fragmentList.get(4)
        }

        if (baseFragment == null) {
            replaceContent(fragment, R.id.fragmentContainer1)
            baseFragment = fragment
        } else {
            if (fragment != null) {
                switchContent(baseFragment, fragment, R.id.fragmentContainer1)
                baseFragment = fragment;
            }
        }
    }
}