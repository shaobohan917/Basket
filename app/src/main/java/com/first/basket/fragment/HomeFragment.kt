package com.first.basket.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.a.AddressActivity
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.constants.Constants
import com.first.basket.view.GoodsView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by hanshaobo on 30/08/2017.
 */
class HomeFragment : BaseFragment() {
    private lateinit var rootview: View
    private var images = ArrayList<String>()
    private val picUrl = Constants.PIC_URL


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootview = inflater?.inflate(R.layout.fragment_home, container, false)!!
        return rootview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        images.add(picUrl)
        images.add(picUrl)
        images.add(picUrl)
        images.add(picUrl)


        val banner = rootview.findViewById<Banner>(R.id.banner)
        banner.setImages(images)
                .setImageLoader(GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(30000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

    }

    private fun initListener() {

        llPosition.setOnClickListener {
            val intent = Intent(activity, AddressActivity::class.java)
            startActivityForResult(intent, 0)
        }

        gvGoods.setOnClickListener {
            startActivity(Intent(activity, GoodsDetailActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val aoi = data?.extras?.get("aoiName")
        val tvAddress = rootview.findViewById<TextView>(R.id.tvAddress)!!
        tvAddress.text = aoi.toString()

    }


    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context).load(path).into(imageView)
        }
    }
}