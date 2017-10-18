package com.first.basket.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.MainActivity
import com.first.basket.activity.PlaceOrderActivity
import com.first.basket.adapter.MenuAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.app.NotifyManager
import com.first.basket.base.HttpResult
import com.first.basket.bean.*
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.item_recycler_shop.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment(), Observer {

    private var isAllChecked: Boolean = false
    private var mGoodsList = ArrayList<ProductBean>()
    private var mGoodsMap = HashMap<ProductBean, Int>()

    private var mAdapter = MenuAdapter(mGoodsList, object : MenuAdapter.OnItemClickListener {
        override fun onItemClick(view: View) {
        }

    }, CompoundButton.OnCheckedChangeListener { p0, p1 ->
        if (p1) {
//            getPrice(mGoodsList)
            notifySubscribeStateChanged("123", 123)
        } else {
            cbSelectAll.isChecked = false
        }
    })

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shop, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NotifyManager.getNotifyManager().addObserver(this)
        initView()
        initData()
        initListener()
        onHiddenChanged(false)
    }

    private fun initData() {
        getHotRecommend()
    }

    private fun getHotRecommend() {
        HttpMethods.createService()
                .getHotRecommend("get_hotrecommend", (activity as MainActivity).mChannel.toString())
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HotRecommendBean>() {
                    override fun onNext(t: HotRecommendBean) {
                        super.onNext(t)
                        setRecommendData(t.result.data)
                    }
                })
    }

    private fun setRecommendData(data: HotRecommendBean.ResultBean.DataBean) {
        val recommendList = data.products
    }

    private fun initView() {
        tvAddress.text = SPUtil.getString(StaticValue.SP_ADDRESS, "")
        ImageUtils.showImg(activity, Constants.PIC_URL, ivBanner)

        smRecyclerView.layoutManager = LinearLayoutManager(activity)
        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)

        smRecyclerView.adapter = mAdapter

        smRecyclerView.setSwipeMenuItemClickListener { closeable, adapterPosition, menuPosition, direction ->
            LogUtils.d("onItemClick:" + adapterPosition)
            closeable.smoothCloseMenu()

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                mGoodsList.removeAt(adapterPosition)
                mAdapter.notifyItemRemoved(adapterPosition)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private val swipeMenuCreator = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, viewType ->
        val width = resources.getDimensionPixelSize(R.dimen.item_height)
        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val deleteItem = SwipeMenuItem(activity)
                .setBackgroundColor(activity.resources.getColor(R.color.red56))
                .setText("删除") // 文字。
                .setTextColor(R.color.white) // 文字颜色。
                .setTextSize(17) // 文字大小。
                .setWidth(width)
                .setHeight(height)
        swipeRightMenu.addMenuItem(deleteItem)// 添加一个按钮到右侧侧菜单。
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val goods = data!!.extras.getSerializable("goods") as GoodsDetailBean.ResultBean.DataBean
//            mGoodsList.add(goods)
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
            if (CommonMethod.isLogin()) {
                var intent = Intent(activity, PlaceOrderActivity::class.java)
                intent.putExtra("price", tvTotalPrice.text)
                startActivity(intent)
            } else {
                CommonMethod.showLogin()
            }
        }
        cbSelectAll.onClick {
            isAllChecked = !isAllChecked
            for (i in 0 until smRecyclerView.childCount) {
                smRecyclerView.getChildAt(i).findViewById<AppCompatCheckBox>(R.id.cbSelect).isChecked = isAllChecked
            }
            if (isAllChecked && mGoodsList.size > 0) {
                getPrice(mGoodsList)
                llTotalPrice.visibility = View.VISIBLE
            } else {
                llTotalPrice.visibility = View.GONE
            }
        }
    }

    private fun getPrice(mDatas: ArrayList<ProductBean>) {
        var productidString = StringBuilder()
        var numString = StringBuilder()

        for (i in 0 until mDatas.size) {
            productidString.append(mDatas[i].productid).append("|")
            numString.append(smRecyclerView.getChildAt(i).amoutView.amount).append("|")
        }
        val ps: String = productidString.toString().substring(0, productidString.length - 1)
        val ns: String = numString.toString().substring(0, numString.length - 1)
        HttpMethods.createService().getPrice("get_price", ps, ns)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<PriceBean>>() {
                    override fun onNext(t: HttpResult<PriceBean>) {
                        super.onNext(t)
                        tvTotalPrice.visibility = View.VISIBLE
                        tvTotalPrice.text = t.result.data.totalprice.toString()
                    }
                })
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mGoodsList.clear()
            mGoodsMap = BaseApplication.getInstance().mGoodsMap

            var iterator = mGoodsMap.entries.iterator()
            while (iterator.hasNext()) {
                var entry = iterator.next() as Map.Entry<ProductBean, Int>
                var key = entry.key
                var value = entry.value

                mGoodsList.add(key)
                Collections.reverse(mGoodsList)
            }
            if (!cbSelectAll.isChecked && mGoodsList.size > 0) {
                cbSelectAll.performClick()
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun update(observable: Observable?, data: Any?) {
        var notifyMsgEntity = data as NotifyMsgEntity
        var type = notifyMsgEntity.code
        if (type == NotifyManager.TYPE_DFH_SUB_STATE_CHANGED) {
        }

    }

    private fun notifySubscribeStateChanged(dfhid: String, isdy: Int) {
        val entity = NotifyMsgEntity(NotifyManager.TYPE_DFH_SUB_STATE_CHANGED, dfhid, isdy)
        NotifyManager.getNotifyManager().notifyChange(entity)
    }
}
