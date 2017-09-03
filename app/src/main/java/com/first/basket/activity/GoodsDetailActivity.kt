package com.first.basket.activity

import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.first.basket.BaseActivity
import com.first.basket.R
import com.first.basket.constants.Constants
import com.first.basket.fragment.HomeFragment
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by hanshaobo on 02/09/2017.
 */
class GoodsDetailActivity : BaseActivity() {
    private val picUrl = Constants.PIC_URL
    private var images = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_detail)
        initView()
    }


    private fun initView() {
        images.add(picUrl)
        banner.setImages(images)
                .setImageLoader(HomeFragment.GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(30000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

        tvPrice.text = getString(R.string.price, "12.00")

    }
}