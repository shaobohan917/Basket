package com.first.basket.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_order_list.*

/**
 * Created by hanshaobo on 12/09/2017.
 */
class OrderListActivity : BaseActivity() {
    var fragmentList = ArrayList<OrderFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        var currentItem = 0
        var listener = View.OnClickListener { p0 ->
            when (p0.id) {
                R.id.rlAll -> {
                    currentItem = 0
                }
                R.id.rlPayNeed -> {
                    currentItem = 1

                }
                R.id.rlSend -> {
                    currentItem = 2

                }
                R.id.rlReceive -> {
                    currentItem = 3
                }
            }
            setItem(currentItem)
            viewPager.currentItem = currentItem
        }
        rlAll.setOnClickListener(listener)
        rlSend.setOnClickListener(listener)
        rlReceive.setOnClickListener(listener)
        rlPayNeed.setOnClickListener(listener)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                setItem(position)
            }

        })
    }

    private fun setItem(currentItem: Int) {
        when (currentItem) {
            0 -> {
                ivAll.visibility = View.VISIBLE
                ivPayNeed.visibility = View.GONE
                ivSend.visibility = View.GONE
                ivReceive.visibility = View.GONE
            }
            1 -> {
                ivAll.visibility = View.GONE
                ivSend.visibility = View.GONE
                ivPayNeed.visibility = View.VISIBLE
                ivReceive.visibility = View.GONE
            }
            2 -> {
                ivAll.visibility = View.GONE
                ivPayNeed.visibility = View.GONE
                ivReceive.visibility = View.GONE
                ivSend.visibility = View.VISIBLE
            }
            3 -> {
                ivAll.visibility = View.GONE
                ivPayNeed.visibility = View.GONE
                ivReceive.visibility = View.VISIBLE
                ivSend.visibility = View.GONE
            }

        }

    }


    private fun initView() {
    }

    private fun initData() {
        for (i in 0 until 4) {
            var fragment = OrderFragment()

            var bundle = Bundle()
            bundle.putString("position", i.toString())
            fragment.arguments = bundle

            fragmentList.add(fragment)
        }

        var mAdapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = mAdapter

        mAdapter.notifyDataSetChanged()
    }

    inner class ViewPagerAdapter(fm: FragmentManager, fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
        var mFragments = fragments
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }
}