package com.first.basket.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.OrderDetailActivity
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.bean.HotRecommendBean
import com.first.basket.bean.PriceBean
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.item_recycler_shop.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {
    private var isAllChecked: Boolean = false
    private var mDatas = ArrayList<GoodsDetailBean.ResultBean.DataBean>()
    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    private var mAdapter = BaseRecyclerAdapter(R.layout.item_recycler_shop, mDatas) { view: View, bean: GoodsDetailBean.ResultBean.DataBean ->
        view.tvName1.text = bean.title.toString()
        view.tvUnit1.text = bean.subtitle.toString()
        view.tvPrice1.text = bean.price.toString()

        view.amoutView.setGoods_storage(10)
        view.amoutView.setOnAmountChangeListener { view, amount ->
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shop, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()
    }

    private fun initData() {
        getHotRecommend()
    }

    private fun getHotRecommend() {
        HttpMethods.createService()
                .getHotRecommend("get_hotrecommend")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<HotRecommendBean>>() {
                    override fun onNext(t: HttpResult<HotRecommendBean>) {
                        super.onNext(t)
                        LogUtils.d("热门推荐长度:" + t.result.data.products.size)
                        setRecommendData(t.result.data)
                    }
                })
    }

    private fun setRecommendData(data: HotRecommendBean.DataBean) {
        val recommendList = data.products
    }

    private fun initView() {
        ImageUtils.showImg(activity, Constants.PIC_URL, ivBanner)

        for (j in contents) {
            var bean = GoodsDetailBean.ResultBean.DataBean()
            bean.title = j
            bean.subtitle = "" + 1
            bean.price = 1f
            mDatas.add(bean)
        }

        smRecyclerView.layoutManager = LinearLayoutManager(activity)
        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)

        smRecyclerView.adapter = mAdapter
3
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private val swipeMenuCreator = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, viewType ->
        val width = resources.getDimensionPixelSize(R.dimen.item_height)
        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val deleteItem = SwipeMenuItem(activity)
                .setBackgroundDrawable(R.color.black)
                .setText("删除") // 文字。
                .setTextColor(R.color.white) // 文字颜色。
                .setTextSize(16) // 文字大小。
                .setWidth(width)
                .setHeight(height)
        swipeRightMenu.addMenuItem(deleteItem)// 添加一个按钮到右侧侧菜单。
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val goods = data!!.extras.getSerializable("goods") as GoodsDetailBean.ResultBean.DataBean
            mDatas.add(goods)
            mAdapter.notifyDataSetChanged()
        }
    }

    fun initListener() {
        gvGoods.onClick {
            var intent = Intent(activity, GoodsDetailActivity::class.java)
//            intent.putExtra("id".)
            startActivityForResult(intent, 0)
        }
        ivBuy.onClick {
            startActivity(Intent(activity, OrderDetailActivity::class.java))
        }
        cbSelectAll.onClick {
            isAllChecked = !isAllChecked
            for (i in 0 until smRecyclerView.childCount) {
                smRecyclerView.getChildAt(i).findViewById<AppCompatCheckBox>(R.id.cbSelect).isChecked = isAllChecked
            }
            if (isAllChecked) {
                getPrice(mDatas)
                llTotalPrice.visibility = View.VISIBLE
            } else {
                llTotalPrice.visibility = View.GONE
            }
        }
    }

    private fun getPrice(mDatas: ArrayList<GoodsDetailBean.ResultBean.DataBean>) {
        var productidString = StringBuilder()
        var numString = StringBuilder()
        for (i in 0 until mDatas.size) {
//            priceString.append(mDatas[i].price)
            productidString.append("10000007").append("|")
            numString.append("1").append("|")
        }
        HttpMethods.createService().getPrice("get_price", productidString.toString().substring(0, productidString.length - 1), numString.toString().substring(0, numString.length - 1))
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<PriceBean>>() {
                    override fun onNext(t: HttpResult<PriceBean>) {
                        super.onNext(t)
                        tvTotalPrice.text = t.result.data.totalprice.toString()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                    }
                })

    }
}
