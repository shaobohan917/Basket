package com.first.basket.fragment

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.AddressListActivity
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.MainActivity
import com.first.basket.activity.PlaceOrderActivity
import com.first.basket.adapter.MenuAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.HttpResult
import com.first.basket.bean.AddressBean
import com.first.basket.bean.HotRecommendBean
import com.first.basket.bean.PriceBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import kotlinx.android.synthetic.main.fragment_shop.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {
    private var mGoodsList = ArrayList<ProductBean>()
    private lateinit var mAdapter: MenuAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shop, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        onHiddenChanged(false)
        initListener()
    }

    private fun initView() {
        smRecyclerView.layoutManager = LinearLayoutManager(activity)
        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)
    }

    private fun initData() {
        mAdapter = MenuAdapter(mGoodsList, object : MenuAdapter.OnItemClickListener {
            override fun onItemClick(view: View) {
            }

        }, object : MenuAdapter.OnItemCheckedListener {
            override fun onItemCheck(view: View, b: Boolean, index: Int) {
                mGoodsList[index].isCheck = b

                getPrice(mGoodsList)
            }
        }, object : MenuAdapter.OnItemAmountChangedListener {
            override fun onItemAmountChanged(view: View, amount: Int, index: Int) {
                if (amount == 0) {
                    (activity as MainActivity).showPop("确定删除该件商品吗？", "", "删除", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            mGoodsList.removeAt(index)
                            refresh()
                        }
                    })
                } else {
                    mGoodsList[index].amount = amount
                    getPrice(mGoodsList)
                }
            }
        })

        smRecyclerView.adapter = mAdapter
        getHotRecommend()
        setDefaultAddress()
    }

    private fun setDefaultAddress() {
        var str = SPUtil.getString(StaticValue.DEFAULT_ADDRESS, "")
        if (!TextUtils.isEmpty(str)) {
            val gson = GsonBuilder().create()
            val addressInfo = gson.fromJson(str, AddressBean::class.java)
            tvAddress.text = addressInfo.street
        } else {
            tvAddress.text = activity.getString(R.string.add_address)
        }
        tvAddress.onClick {
            startActivityForResult(Intent(activity, AddressListActivity::class.java), 101)
        }
    }

    private fun refresh() {
        mAdapter.notifyDataSetChanged()
    }

    fun initListener() {
        smRecyclerView.setSwipeMenuItemClickListener { closeable, adapterPosition, menuPosition, direction ->
            LogUtils.d("onItemClick:" + adapterPosition)
            closeable.smoothCloseMenu()

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                mGoodsList.removeAt(adapterPosition)
                mAdapter.notifyItemRemoved(adapterPosition)
                mAdapter.notifyDataSetChanged()
            }
        }

        gvGoods.onClick {
            var intent = Intent(activity, GoodsDetailActivity::class.java)
            startActivityForResult(intent, 0)
        }
        ivBuy.onClick {
            if (!CommonMethod.isLogin()) {
                (activity as MainActivity).showLogin()
                return@onClick
            }
            if (TextUtils.isEmpty(SPUtil.getString(StaticValue.DEFAULT_ADDRESS, ""))) {
                ToastUtil.showToast(activity.getString(R.string.add_address))
                return@onClick
            }
            if (!cbSelectAll.isChecked) {
                ToastUtil.showToast("请选择全部商品")
                return@onClick
            }
            var intent = Intent(activity, PlaceOrderActivity::class.java)
            intent.putExtra("price", tvTotalPrice.text)
            startActivity(intent)
        }

        cbSelectAll.setOnCheckedChangeListener { compoundButton, b ->
            LogUtils.d("b:" + b)
            if (b) {
                for (i in 0 until mGoodsList.size) {
                    mGoodsList[i].isCheck = true
                }
                mAdapter.notifyDataSetChanged()
                getPrice(mGoodsList)
            } else {
                for (i in 0 until mGoodsList.size) {
                    mGoodsList[i].isCheck = false
                }
                mAdapter.notifyDataSetChanged()
                llTotalPrice.visibility = View.GONE

            }
        }

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
            if (requestCode == 101) {
                //设置地址
                var addressInfo = data?.getSerializableExtra("addressInfo") as AddressBean?
                tvAddress.text = addressInfo?.street
                SPUtil.setString(StaticValue.DEFAULT_ADDRESS, Gson().toJson(addressInfo))
            }
        }
    }


    private fun getPrice(mDatas: ArrayList<ProductBean>) {
        if (mDatas.size > 0) {

            //选择所有isCheck=true的
            var mSelectProductsList = ArrayList<ProductBean>()
            (0 until mDatas.size)
                    .filter { mDatas[it].isCheck }
                    .mapTo(mSelectProductsList) { mDatas[it] }

            var productidString = StringBuilder()
            var numString = StringBuilder()

            for (i in 0 until mSelectProductsList.size) {
                productidString.append(mSelectProductsList[i].productid).append("|")
                numString.append(mSelectProductsList[i].amount).append("|")
            }
            if (!TextUtils.isEmpty(productidString) && !TextUtils.isEmpty(numString)) {
                val ps: String = productidString.toString().substring(0, productidString.length - 1)
                val ns: String = numString.toString().substring(0, numString.length - 1)
                HttpMethods.createService().getPrice("get_price", ps, ns)
                        .compose(TransformUtils.defaultSchedulers())
                        .subscribe(object : HttpResultSubscriber<HttpResult<PriceBean>>() {
                            override fun onNext(t: HttpResult<PriceBean>) {
                                super.onNext(t)
                                LogUtils.d("jiage:" + t.result.data.totalcost)
                                setPrice(t.result.data.totalcost)
                            }
                        })
            }
        }
    }

    private fun setPrice(totalcost: Float) {
        llTotalPrice.visibility = View.VISIBLE
        tvTotalPrice.text = "¥ " + totalcost.toString()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            //回到购物车页面
            cbSelectAll.isChecked = true
            setShopData()
            for (i in 0 until mGoodsList.size) {
                mGoodsList[i].isCheck = true
            }

            getPrice(mGoodsList)
        } else {
            BaseApplication.getInstance().setProductsList(mGoodsList)
            (activity as MainActivity).setCount()
        }
    }

    private fun setShopData() {
        var oldList = BaseApplication.getInstance().getProductsList()
        if (oldList != null && oldList.size > 0) {
            Collections.reverse(oldList)
            var list = ArrayList<ProductBean>()

            (0 until oldList.size)
                    .filter { "1".equals(oldList[it].channelid) }
                    .mapTo(list) { oldList[it] }
            (0 until oldList.size)
                    .filter { "2".equals(oldList[it].channelid) }
                    .mapTo(list) { oldList[it] }
            (0 until oldList.size)
                    .filter { "3".equals(oldList[it].channelid) }
                    .mapTo(list) { oldList[it] }

            mGoodsList.clear()
            mGoodsList.addAll(list)
            mAdapter.notifyDataSetChanged()
            LogUtils.d("长度：" + mGoodsList.size)
//
//            //设置全选
//            cbSelectAll.isChecked = false
//            cbSelectAll.isChecked = true
        }
    }
}
