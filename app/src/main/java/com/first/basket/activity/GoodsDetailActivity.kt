package com.first.basket.activity

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.first.basket.BaseActivity
import com.first.basket.R
import com.first.basket.fragment.HomeFragment
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer

/**
 * Created by hanshaobo on 02/09/2017.
 */
class GoodsDetailActivity : BaseActivity() {
    private val picUrl = "http://07.imgmini.eastday.com/mobile/20170831/20170831164106_cd214bb067a8c130816b5827b4231ac0_1.jpeg"
    @BindView(R.id.banner)
    lateinit var banner: Banner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_detail)
        initView()
    }

    private lateinit var images: ArrayList<String>

    private fun initView() {
        images.add(picUrl)
//        var banner = findViewById<Banner>(R.id.banner)
        banner.setImages(images)
                .setImageLoader(HomeFragment.GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(30000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

    }
}