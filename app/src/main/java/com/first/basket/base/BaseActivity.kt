package com.first.basket.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.first.basket.rxjava.RxjavaUtil
import com.first.basket.rxjava.UITask

/**
 * Created by hanshaobo on 30/08/2017.
 */
open class BaseActivity : AppCompatActivity() {

    val REQUEST_ONE = 101
    val REQUEST_TWO = 102
    val REQUEST_THREE = 103
    val REQUEST_SPE = 100

    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressDialog = ProgressDialog(this@BaseActivity)
    }

    fun replaceContent(fragment: Fragment, fragmentResId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(fragmentResId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun switchContent(from: Fragment, to: Fragment, id: Int) {
        if (from !== to) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (!to.isAdded) {
                transaction.hide(from).add(id, to).commitAllowingStateLoss()
            } else {
                transaction.hide(from).show(to).commitAllowingStateLoss()
            }
        }
    }


    fun showProgressDialog(message: String) {
        RxjavaUtil.doInUIThread(object : UITask<Any>() {
            override fun doInUIThread() {
                mProgressDialog = ProgressDialog(this@BaseActivity)
                mProgressDialog.setCanceledOnTouchOutside(false)
                mProgressDialog.setMessage(message)
                mProgressDialog.show()
            }
        })
    }

//    fun showProgressDialog() {
//        showProgressDialog("加载中...")
//    }
//
//
//    fun hideProgress() {
//        RxjavaUtil.doInUIThread(object : UITask<Any>() {
//            override fun doInUIThread() {
//                if (mProgressDialog.isShowing) {
//                    mProgressDialog.hide()
//                }
//            }
//        })
//    }

    fun myStartActivity(cls: Class<*>, needFinish: Boolean) {
        val intent = Intent(this, cls)
        startActivity(intent)
        if (needFinish) this.finish()
    }

    fun myStartActivity(intent: Intent, needFinish: Boolean) {
        startActivity(intent)
        if (needFinish) this.finish()
    }

    fun myStartActivity(intent: Intent) {
        myStartActivity(intent, false)
    }

    fun myStartActivityForResult(cls: Class<*>, requestCode: Int) {
        val intent = Intent(this, cls)
        startActivityForResult(intent, requestCode)
    }

    fun myStartActivityForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    fun myFinish() {
        finish()
    }

    fun showDialog(title: String, content: String, positive: String, listener: DialogInterface.OnClickListener) {
        var dialog = AlertDialog.Builder(this@BaseActivity)
        dialog.setTitle(title)
        dialog.setMessage(content)
        dialog.setPositiveButton(positive, listener)
        dialog.setNegativeButton("取消") { p0, p1 -> p0?.dismiss() }
        dialog.show()
    }

    fun showDialog(title: String, listener: DialogInterface.OnClickListener) {
        showDialog(title, "", "确定", listener)
    }

}