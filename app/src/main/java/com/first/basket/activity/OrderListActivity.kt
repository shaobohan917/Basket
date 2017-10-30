package com.first.basket.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.widget.RadioButton
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_orderlist.*
import org.jetbrains.anko.radioButton

/**
 * Created by hanshaobo on 12/09/2017.
 */
class OrderListActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderlist)
        initView()
        initData()
    }


    private fun initView() {

    }

    private fun initData() {
        var fragmentList = ArrayList<OrderFragment>()
        var fragments = ArrayList<String>()
        for (i in 0 until 1) {
            var fragment = OrderFragment()
            fragmentList.add(fragment)
            fragments.add(fragment::class.java.name)
        }

        class MyApater(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

            override fun getItem(position: Int): Fragment {

                return Fragment.instantiate(context, fragments[position])
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }

        viewPager.adapter = MyApater(supportFragmentManager, this@OrderListActivity)
    }

}