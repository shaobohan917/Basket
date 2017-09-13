package com.first.basket.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.activity.AddressInfoActivity
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.SearchActivity
import com.first.basket.bean.HomeBean
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.ScreenUtils
import com.first.basket.view.GoodsView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.wrapContent
import java.util.*
import android.R.attr.data
import cn.ymex.banner.Banner.BindViewCallBack


/**
 * Created by hanshaobo on 30/08/2017.
 */
class HomeFragment : BaseFragment() {
    private var images = ArrayList<String>()
    private var images1 = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    private fun initData() {
        class Sub : HttpResultSubscriber<HomeBean>() {
            override fun onNext(t: HomeBean) {
                super.onNext(t)
                setData(t.result!!.data!!)
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

        llPosition.setOnClickListener {
            val intent = Intent(activity, AddressInfoActivity::class.java)
            startActivityForResult(intent, 0)
        }

        ivSearch.onClick { startActivity(Intent(activity, SearchActivity::class.java)) }
//        gvGoods.setOnClickListener {
//            startActivity(Intent(activity, GoodsDetailActivity::class.java))
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val aoi = data?.extras?.get("aoiName")
        tvAddress.text = aoi.toString()

    }


    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context).load(path).into(imageView)
        }
    }


    private fun setData(data: HomeBean.ResultBean.DataBean) {
        //设置banner
        (0 until data.carouselfigure.size)
                .map { Constants.BASE_IMG_URL + data.carouselfigure[it].image }
                .forEach { images.add(it) }
        banner.setImages(images)
                .setImageLoader(GlideImageLoader())
                .setBannerAnimation(Transformer.Accordion)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

        //设置垂直轮播
        for (i in 0 until data.sqcs.carouselfigure.size) {
            images1.add(Constants.BASE_IMG_URL + data.sqcs.carouselfigure[i].image)
        }
        vBanner.bindView(object : cn.ymex.banner.Banner.BindViewCallBack<AppCompatImageView, String> {
            override fun bindView(view: AppCompatImageView, data: String, position: Int) {
                //图片加载
                Glide.with(view.context)
                        .load(data)
                        .into(view)
            }
        }).setOrientation(cn.ymex.banner.Banner.VERTICAL).execute(images1)


        ImageUtils.showImg(activity, data.sqcs.vegetables, vegetables)
        ImageUtils.showImg(activity, data.sqcs.meat, meat)

        //设置推荐商品
        setRecommendData(data)
    }

    private fun setRecommendData(data: HomeBean.ResultBean.DataBean) {
        val recommendList = data.yhcs!!.recommend as List<HomeBean.ResultBean.DataBean.YHCSBean.RecommendBean>
        for (i in 0 until recommendList.size) {
            val yhcs = recommendList[i]
            val goodsView = GoodsView(activity)
            val params = LinearLayout.LayoutParams(ScreenUtils.dip2px(activity, 90f), matchParent)
//            val params = LinearLayout.LayoutParams(wrapContent, wrapContent)
            params.setMargins(0, 0, 20, 0)
            goodsView.layoutParams = params
            goodsView.setGoods(yhcs.productname!!, yhcs.unit!!, yhcs.price!!, yhcs.img, 90)
            llGoods.addView(goodsView)
            goodsView.onClick {
                var intent = Intent(activity, GoodsDetailActivity::class.java)
                intent.putExtra("id", yhcs.productid)
                startActivity(intent)
            }
        }
    }
}
