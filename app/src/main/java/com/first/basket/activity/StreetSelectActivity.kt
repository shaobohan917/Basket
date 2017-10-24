package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.DistrictBean
import kotlinx.android.synthetic.main.activity_street_select.*
import kotlinx.android.synthetic.main.item_recycler_district.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 24/10/2017.
 */
class StreetSelectActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street_select)
        initData()
        initView()
    }

    private lateinit var mDatas: ArrayList<DistrictBean.DataBean>

    private fun initData() {
        mDatas = intent.getSerializableExtra("list") as ArrayList<DistrictBean.DataBean>
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = BaseRecyclerAdapter(R.layout.item_recycler_district, mDatas) { view: View, item: DistrictBean.DataBean ->
            view.tvDistrict.text = item.subdistrict
            view.tvDistrict.onClick {
                intent.putExtra("subdistrict", item.districtid)
                setResult(Activity.RESULT_OK, intent)
                myFinish()
            }

        }
        recyclerView.adapter = adapter
    }
}