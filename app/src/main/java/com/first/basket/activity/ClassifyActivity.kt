package com.first.basket.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.fragment.ClassifyFragment

/**
 * Created by hanshaobo on 10/10/2017.
 */
class ClassifyActivity : BaseActivity() {
    private var channel: String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classify)

        channel = intent.getStringExtra("channel")
        var classifyFragment = ClassifyFragment()
        var bundle = Bundle()
        bundle.putString("channel", channel)
        classifyFragment.arguments = bundle
        var transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(R.id.fragmentContainer, classifyFragment).commit()
    }
}