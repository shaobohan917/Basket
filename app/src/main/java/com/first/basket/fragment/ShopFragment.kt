package com.first.basket.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.adapter.ShopAdapter
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.BaseBean
import com.first.basket.bean.ShopBean
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResult
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.item_recycler_shop.view.*
import rx.Observable


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {
    private var mDatas = ArrayList<ShopBean>()
    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_shop, container, false)!!
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()


//        var map: HashMap<String, String> = HashMap()
//        HttpMethods.createService()
//                .getDistrict(map)
//                .compose(TransformUtils.defaultSchedulers())
//                .subscribe(object : HttpResultSubscriber<BaseBean>() {
//                    override fun onSuccess(result: BaseBean?, reason: String?) {
//                        LogUtils.d("result:" + result)
//
//                    }
//
//                    override fun _onError(code: Int, throwable: Throwable, result: BaseBean?) {
//                        LogUtils.d("_onError:" + throwable.message)
//                    }
//
//                    override fun onCompleted() {
//                        super.onCompleted()
//                        LogUtils.d("onCompleted")
//                    }
//                })

    }

    private fun initView() {
        gvGoods.setOnClickListener { startActivity(Intent(activity, GoodsDetailActivity::class.java)) }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        ImageUtils.loadUrl(activity, Constants.PIC_URL, ivBanner)

        for (j in contents) {
            var bean = ShopBean()
            bean.name = j
            bean.num = 1
            bean.price = 1f
            mDatas.add(bean)
        }
        recyclerView.adapter = ShopAdapter(mDatas)

        smRecyclerView.layoutManager = LinearLayoutManager(activity)
        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)

        val adapter = BaseRecyclerAdapter(R.layout.item_recycler_shop, mDatas) { view: View, shopBean: ShopBean ->
            view.tvName.text = shopBean.name
            view.tvNum.text = shopBean.num.toString()
            view.tvPrice.text = shopBean.price.toString()
        }

        smRecyclerView.adapter = adapter
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
}
