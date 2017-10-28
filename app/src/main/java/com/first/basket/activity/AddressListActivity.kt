package com.first.basket.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.adapter.AddressMenuAdapter
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.AddressBean
import com.first.basket.bean.AddressListBean
import com.first.basket.bean.CodeBean
import com.first.basket.common.CommonMethod1
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import com.google.gson.Gson
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem
import kotlinx.android.synthetic.main.activity_address_list.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by hanshaobo on 10/09/2017.
 */
class AddressListActivity : BaseActivity() {
    private var mDatas = ArrayList<AddressBean>()
    private lateinit var mAdapter: AddressMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        initData()
        initView()
        initListener()
    }

    private fun initData() {
        getAddressList()

        mAdapter = AddressMenuAdapter(mDatas, object : AddressMenuAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                if (mDatas.size > 0) {
                    showChoose(mDatas[position].street.replace("&", " "), position)
                }
            }

        }, object : AddressMenuAdapter.OnItemCheckedListener {
            override fun onItemDelete(position: Int) {
                if (mDatas.size > 0) {
                    showDelete(mDatas[position].street.replace("&", " "), position)
                }
            }

            override fun onItemModify(position: Int) {
                if (mDatas.size > 0) {
                    var intent = Intent(this@AddressListActivity, AddressAddActivity::class.java)
                    intent.putExtra("from", 1)
                    intent.putExtra("address", mDatas[position])
                    myStartActivityForResult(intent, REQUEST_ONE)
                }
            }

            override fun onItemChecked(addressid: String) {
                doDefaultAddress(addressid)
            }
        })
    }

    fun showChoose(str: String, position: Int) {
        showDialog("提示", "本次配送地址为：" + str, "确定", DialogInterface.OnClickListener { p0, p1 ->
            var addressInfo = mDatas[position]
            addressInfo.street = addressInfo.street.replace("&", " ")
            SPUtil.setString(StaticValue.DEFAULT_ADDRESS, Gson().toJson(addressInfo))
            intent.putExtra("addressInfo", addressInfo)
            setResult(Activity.RESULT_OK, intent)
            myFinish()
        })
    }

    fun showDelete(str: String, position: Int) {
        showDialog("提示", "确定删除：" + str, "确定", DialogInterface.OnClickListener { p0, p1 ->
            deleteAddress(position)
        })
    }

    private fun getAddressList() {
        CommonMethod1.showLoading(loadingView)
        HttpMethods.createService()
                .getAddressList("get_useraddress", SPUtil.getString(StaticValue.USER_ID, ""))
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<AddressListBean>>() {
                    override fun onNext(t: HttpResult<AddressListBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            setAddressList(t.result)
                        } else if (t.status == 1) {
                            mDatas.clear()
                            mAdapter.notifyDataSetChanged()
                            SPUtil.setString(StaticValue.DEFAULT_ADDRESS, "")
                        }
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        CommonMethod1.hideLoading(loadingView)
                    }
                })
    }

    private fun setAddressList(result: AddressListBean) {
        mDatas.clear()
        mDatas.addAll(result.data)
        mAdapter.notifyDataSetChanged()
    }


    private fun initView() {
        smRecyclerView.layoutManager = LinearLayoutManager(this@AddressListActivity)
//        smRecyclerView.setSwipeMenuCreator(swipeMenuCreator)
        smRecyclerView.isItemViewSwipeEnabled = false

        smRecyclerView.adapter = mAdapter
    }

    private fun initListener() {

        btAdd.onClick {
            myStartActivityForResult(Intent(this@AddressListActivity, AddressAddActivity::class.java), REQUEST_ONE)
        }
    }

    private fun deleteAddress(position: Int) {
        HttpMethods.createService()
                .doDeleteAddress("do_deleteaddress", SPUtil.getString(StaticValue.USER_ID, ""), mDatas[position].addressid)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<CodeBean>>() {
                    override fun onNext(t: HttpResult<CodeBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            getAddressList()
                        }
                    }
                })
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private val swipeMenuCreator = SwipeMenuCreator { swipeLeftMenu, swipeRightMenu, viewType ->
        val width = resources.getDimensionPixelSize(R.dimen.item_height)
        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        val deleteItem = SwipeMenuItem(this@AddressListActivity)
                .setBackgroundColor(resources.getColor(R.color.red56))
                .setText("删除") // 文字。
                .setTextColor(R.color.white) // 文字颜色。
                .setTextSize(17) // 文字大小。
                .setWidth(width)
                .setHeight(height)
        swipeRightMenu.addMenuItem(deleteItem)// 添加一个按钮到右侧侧菜单。
    }


    private fun doDefaultAddress(addressid: String) {
        HttpMethods.createService()
                .doDefaultAddress("do_defaultaddress", SPUtil.getString(StaticValue.USER_ID, ""), addressid)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<CodeBean>>() {
                    override fun onNext(t: HttpResult<CodeBean>) {
                        super.onNext(t)
                        getAddressList()
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ONE) {
                getAddressList()
            }
        }
    }
}