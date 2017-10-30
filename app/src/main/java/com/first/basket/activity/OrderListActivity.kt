package com.first.basket.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_orderlist.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 12/09/2017.
 */
class OrderListActivity : BaseActivity() {
    var fragmentList = ArrayList<OrderFragment>()
    var fragments = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderlist)
        initView()
        initData()
    }


    private fun initView() {
        rlAll.onClick {
            viewPager.currentItem = 0
        }
        rlPaied.onClick {
            viewPager.currentItem = 1
        }
        rlPayNeed.onClick {
            viewPager.currentItem = 2
        }
    }

    private fun initData() {
        for (i in 0 until 3) {
            var fragment = OrderFragment()

            var bundle = Bundle()
            bundle.putString("position", i.toString())
            fragment.arguments = bundle

            fragmentList.add(fragment)
            fragments.add(fragment::class.java.name)
        }

        var mAdapter = ViewPagerAdapter(supportFragmentManager, this@OrderListActivity, fragmentList)
        viewPager.adapter = mAdapter

        mAdapter.notifyDataSetChanged()
    }

    inner class ViewPagerAdapter(fm: FragmentManager, private val context: Context, fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return Fragment.instantiate(context, fragments[position])
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}