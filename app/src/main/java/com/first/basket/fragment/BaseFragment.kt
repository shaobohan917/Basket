package com.first.basket.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction


/**
 * Created by hanshaobo on 30/08/2017.
 */
open class BaseFragment : Fragment() {


    fun replaceContent(fragment: Fragment, fragmentResId: Int) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(fragmentResId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun switchContent(from: Fragment, to: Fragment, id: Int) {
        if (from !== to) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (!to.isAdded) {
                transaction.hide(from).add(id, to).commitAllowingStateLoss()
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss()
            }
        }
    }
}