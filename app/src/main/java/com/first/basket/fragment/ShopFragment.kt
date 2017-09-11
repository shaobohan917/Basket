package com.first.basket.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.OrderActivity
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.constants.Constants
import com.first.basket.utils.ImageUtils
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.item_recycler_shop.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {

    private var mDatas = ArrayList<GoodsDetailBean.ResultBean.DataBean>()
    private var mAdapter = BaseRecyclerAdapter(R.layout.item_recycler_shop, mDatas) { view: View, bean: GoodsDetailBean.ResultBean.DataBean ->
        view.tvName.text = bean.title.toString()
        view.tvUnit.text = bean.subtitle.toString()
        view.tvPrice.text = bean.price.toString()
    }


    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_shop, container, false)!!
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()


//        var map: HashMap<String, String> = HashMap()
//        HttpMethods.createService()
//                .getDistrict(map)
//                .compose(TransformUtils.defaultSchedulers())
//                .subscribe(object : HttpResultSubscriber<BaseBean>() {
//                    override fun onSuccess(result: BaseBean?, info: String?) {
//                        LogUtils.d("result:" + result)
//
//                    }
//
//                    override fun _onError(status: Int, throwable: Throwable, result: BaseBean?) {
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
        recyclerView.layoutManager = LinearLayoutManager(activity)
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
            startActivityForResult(Intent(activity, GoodsDetailActivity::class.java), 0)
        }
        ivBuy.onClick {
            startActivity(Intent(activity, OrderActivity::class.java))
        }
    }
}
