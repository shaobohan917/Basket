package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.bean.HomeBean
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import kotlinx.android.synthetic.main.fragment_content.*
import java.util.*

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyContentFragment : BaseFragment() {
    private lateinit var rootview: View
    private var images = ArrayList<String>()
    private val picUrl = Constants.PIC_URL


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootview = inflater?.inflate(R.layout.fragment_content, container, false)!!
        return rootview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)


        class Sub : HttpResultSubscriber<HomeBean>() {
            override fun onNext(t: HomeBean) {
                super.onNext(t)
            }

            override fun onError(e: Throwable) {
                super.onError(e)
            }

        }

        HttpMethods.createService().getHome("get_mainpage")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(Sub())
    }

    private fun initListener() {
    }
}