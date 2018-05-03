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
import android.widget.LinearLayout
import com.first.basket.R
import com.first.basket.activity.*
import com.first.basket.adapter.ShopAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.AddressBean
import com.first.basket.bean.HotRecommendBean
import com.first.basket.bean.PriceBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.CommonMethod1
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.*
import com.first.basket.view.FullyLinearLayoutManager
import com.google.gson.GsonBuilder
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.layout_view_goods.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {
    private var mGoodsList = ArrayList<ProductBean>()
    private lateinit var mAdapter: ShopAdapter
    private var isFirst: Boolean = true
    private var isModifyMode: Boolean = false
    private var addressInfo = AddressBean()
    private var mTotalcost: Double = 0.0
    private lateinit var mPriceBean: PriceBean.DataBean

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shop, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        onHiddenChanged(false)
        initListener()
        tvAddress.onClick { }
    }

    private fun initView() {
        smRecyclerView.layoutManager = LinearLayoutManager(activity)
//        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)

        titleView.setOnMoreClickListener(
                View.OnClickListener {
                    isModifyMode = !isModifyMode
                    if (isModifyMode) {
                        titleView.setMoreText("完成")
                    } else {
                        titleView.setMoreText("编辑")
                        getPrice(mGoodsList)
                    }
                    setStatus(isModifyMode)
                })
    }

    private fun setStatus(modifyMode: Boolean) {
        if (modifyMode) {
            //编辑模式
            btBuy.text = "删除"
            llTotalPrice.visibility = View.GONE
            mAdapter.setIsModifyMode(true)
        } else {
            btBuy.text = "去结算"
            llTotalPrice.visibility = View.VISIBLE
            mAdapter.setIsModifyMode(false)
        }

    }

    private fun initData() {
        mAdapter = ShopAdapter((activity as MainActivity), mGoodsList, object : ShopAdapter.OnItemClickListener {
            override fun onItemClick(view: View) {
            }

        }, object : ShopAdapter.OnItemCheckedListener {
            override fun onItemCheck(view: View, b: Boolean, index: Int) {
                if (!isModifyMode) {
                    mGoodsList[index].isCheck = b
                    getPrice(mGoodsList)
                }
                llTotalPrice.visibility = if (mGoodsList.any { it.isCheck }) (View.VISIBLE) else (View.GONE)
                if (mGoodsList.all { it.isCheck }) {
                    cbSelectAll.isChecked = true
                }
                if (mGoodsList.all { !it.isCheck }) {
                    cbSelectAll.isChecked = false
                }
            }
        }, object : ShopAdapter.OnItemAmountClickListener {
            override fun OnItemAmountAddClick(view: View, amount: Int, position: Int) {
                if (isModifyMode) {
                    ToastUtil.showToast("编辑模式不可加")
                } else {
                    var count = amount
                    count++
                    mGoodsList[position].isCheck = true
                    mGoodsList[position].amount = count
                    mAdapter.notifyDataSetChanged()
                    getPrice(mGoodsList)
                }
            }

            override fun OnItemAmountSubClick(view: View, amount: Int, position: Int) {
                if (isModifyMode) {
                    ToastUtil.showToast("编辑模式不可减")
                } else {
                    var count = amount
                    if (count > 1) {
                        count--
                        mGoodsList[position].isCheck = true
                        mGoodsList[position].amount = count
                        mAdapter.notifyDataSetChanged()
                        getPrice(mGoodsList)
                    } else {
                        (activity as MainActivity).showDialog("确定删除该商品吗？", "", "确定", DialogInterface.OnClickListener { p0, p1 ->
                            if (CommonMethod.isTrue(mGoodsList[position].promboolean)) {

                            }
                            mGoodsList.removeAt(position)
                            mAdapter.notifyDataSetChanged()
                            getPrice(mGoodsList)
                        })

                    }
                }
            }
        })

        smRecyclerView.adapter = mAdapter
        getHotRecommend()
    }


    private fun setDefaultAddress() {
        if (!CommonMethod.isLogin()) {
            tvAddress.text = "请登录"
            tvAddress.onClick {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
            return
        }
        var str = SPUtil.getString(StaticValue.DEFAULT_ADDRESS, "")
        if (!TextUtils.isEmpty(str)) {
            val gson = GsonBuilder().create()
            addressInfo = gson.fromJson(str, AddressBean::class.java)
            tvAddress.text = addressInfo.street.replace("&", " ")
        } else {
            tvAddress.text = activity.getString(R.string.add_address)
        }
        tvAddress.onClick {
            startActivityForResult(Intent(activity, AddressListActivity::class.java), 101)
        }

    }

    fun initListener() {
        smRecyclerView.setSwipeMenuItemClickListener { closeable, adapterPosition, menuPosition, direction ->
            closeable.smoothCloseMenu()

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                mGoodsList.removeAt(adapterPosition)
                mAdapter.notifyItemRemoved(adapterPosition)
                mAdapter.notifyDataSetChanged()
            }
        }

        btBuy.onClick {
            if (isModifyMode) {
                mGoodsList
                        .filter { it.isCheck }
                        .forEach {
                            mGoodsList.remove(it)
                            if ("荤" == it.promdata.promproducttype) {
                                SPUtil.setBoolean(StaticValue.PROM_HUN, false)
                            } else if ("素" == it.promdata.promproducttype) {
                                SPUtil.setBoolean(StaticValue.PROM_SU, false)
                            }
                        }
                mAdapter.notifyDataSetChanged()
            } else {
                if (!CommonMethod.isLogin()) {
                    (activity as MainActivity).showLogin()
                    return@onClick
                }
                if (TextUtils.isEmpty(SPUtil.getString(StaticValue.DEFAULT_ADDRESS, ""))) {
                    ToastUtil.showToast(activity.getString(R.string.add_address))
                    return@onClick
                }
                //符合菜市的商品
                var list = ArrayList<ProductBean>()
                list.addAll(mGoodsList)
                list = (filterCaishi(list))
                if (list.any { it.isCheck }) {
                    var intent = Intent(activity, PayOrderActivity::class.java)
                    intent.putExtra("price", mTotalcost)
                    intent.putExtra("priceBean", mPriceBean)
                    startActivity(intent)
                } else {
                    ToastUtil.showToast(activity.getString(R.string.buy))
                }
            }
        }

        cbSelectAll.setOnCheckedChangeListener { _, b ->
            for (productBean in mGoodsList) {
                productBean.isCheck = b
            }
            try {
                mAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                LogUtils.d("exception:" + e.message)
            }

            if (b) {
                getPrice(mGoodsList)
            } else {
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
        ImageUtils.showImg(activity, data.hotimage, ivRecommend)
//        recommendRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        var linearLayoutManager = FullyLinearLayoutManager(activity, 2)
        recommendRecyclerView.isNestedScrollingEnabled = false
        //设置布局管理器
        recommendRecyclerView.layoutManager = linearLayoutManager

        val recommedDatas = data.products

        var mAdapter = BaseRecyclerAdapter(R.layout.layout_view_goods, recommedDatas) { view: View, item: ProductBean ->
            view.tvName.text = item.productname
            view.tvPrice.text = "¥ " + CommonMethod1.showPrice(item)
            view.tvUnit.text = item.weight + "/" + item.unit
            ImageUtils.showImg(activity, item.img, view.ivGoods)
            view.llRoot.onClick {
                var intent = Intent(activity, GoodsDetailActivity::class.java)
                intent.putExtra("id", item.productid)
                startActivity(intent)
            }

            var width = ScreenUtils.getScreenWidth(activity)
            var params = LinearLayout.LayoutParams(width / 2, width / 2)
            view.ivGoods.layoutParams = params
        }

        recommendRecyclerView.adapter = mAdapter
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
            if (requestCode == 101 && data != null) {
                //设置地址
                addressInfo = data.getSerializableExtra("addressInfo") as AddressBean
                tvAddress.text = addressInfo!!.street?.replace("&", " ") ?: ""
                mAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun getPrice(mDatas: ArrayList<ProductBean>) {
        if (isModifyMode) return

        var selectProductsList = ArrayList<ProductBean>()
        //选择所有isCheck=true的
        (0 until mDatas.size)
                .filter { mDatas[it].isCheck }
                .mapTo(selectProductsList) { mDatas[it] }
        //再选择所有菜市配送范围内的
        selectProductsList = filterCaishi(selectProductsList)

        if (selectProductsList.size == 0) {
            setPrice(0.0, 0.0)
        } else {
            var productidString = StringBuilder()
            var numString = StringBuilder()

            for (i in 0 until selectProductsList.size) {
                productidString.append(selectProductsList[i].productid).append("|")
                numString.append(selectProductsList[i].amount).append("|")
            }
            if (!TextUtils.isEmpty(productidString) && !TextUtils.isEmpty(numString)) {
                val ps: String = productidString.toString().substring(0, productidString.length - 1)
                val ns: String = numString.toString().substring(0, numString.length - 1)
                HttpMethods.createService().getPrice("get_price", ps, ns, addressInfo.addressid)
                        .compose(TransformUtils.defaultSchedulers())
                        .subscribe(object : HttpResultSubscriber<HttpResult<PriceBean>>() {
                            override fun onNext(t: HttpResult<PriceBean>) {
                                super.onNext(t)
                                if (t.status == 0) {
                                    mTotalcost = t.result.data.allprice
                                    mPriceBean = t.result.data
                                    setPrice(t.result.data.allprice, t.result.data.fare)
                                } else {
                                    ToastUtil.showToast(t.info)
                                }
                            }
                        })
            } else {
                mTotalcost = 0.0
            }
        }
    }

    private fun filterCaishi(selectProductsList: ArrayList<ProductBean>): ArrayList<ProductBean> {
        var iterator = selectProductsList.iterator()
        while (iterator.hasNext()) {
            var productBean = iterator.next()
            if (productBean.channelid == "1" && !CommonMethod.isTrue(addressInfo!!.issqcs)) {
                //社区菜市不支持
                iterator.remove()
            }
            if (productBean.channelid == "2" && !CommonMethod.isTrue(addressInfo!!.isshcs)) {
                //上海菜市不支持
                iterator.remove()
            }
            if (productBean.channelid == "3" && !CommonMethod.isTrue(addressInfo!!.isqgcs)) {
                //全国菜市不支持
                iterator.remove()
            }
        }
        return selectProductsList
    }

    private fun setPrice(totalcost: Double, fare: Double) {
        if (totalcost == 0.0) {
            tvTotalPrice.text = ""
            tvPostage.text = ""
            llTotalPrice.visibility = View.GONE
        } else {
            tvTotalPrice.text = activity.getString(R.string.total_price, totalcost.toString())

            tvPostage.text = if (0.00 == fare) {
                "免运费"
            } else {
                "含运费:" + fare
            }
            llTotalPrice.visibility = View.VISIBLE
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            //回到购物车页面
            setDefaultAddress()
            if (isFirst) {
                setShopData()
                cbSelectAll.isChecked = mGoodsList.size > 0
                llTotalPrice.visibility = if (mGoodsList.size > 0) (View.VISIBLE) else (View.GONE)
                for (i in 0 until mGoodsList.size) {
                    mGoodsList[i].isCheck = true
                }
                getPrice(mGoodsList)
                isFirst = false
            } else {
                var list = ArrayList<ProductBean>()
                BaseApplication.getInstance().productsList = mGoodsList
                mGoodsList.filter { it.channelid == "1" }.forEach {
                    list.add(it)
                }
                mGoodsList.filter { it.channelid == "3" }.forEach {
                    list.add(it)
                }
                mGoodsList.clear()
                mGoodsList.addAll(list)
                getPrice(mGoodsList)
            }
            mAdapter.notifyDataSetChanged()
            tvShopEmpty.visibility = if (mGoodsList.size == 0) (View.VISIBLE) else ((View.GONE))
            if (mGoodsList.size == 0) {
                mTotalcost = 0.0
            }
        } else {
            isModifyMode = false
            titleView.setMoreText("编辑")
            setStatus(isModifyMode)

            BaseApplication.getInstance().productsList = mGoodsList
            (activity as MainActivity).setCount()
            isModifyMode = false
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        onHiddenChanged(false)
    }

    private fun setShopData() {
        var oldList = BaseApplication.getInstance().productsList
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
        }
    }
}
