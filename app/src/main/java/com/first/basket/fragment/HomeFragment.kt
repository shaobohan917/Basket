package com.first.basket.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.activity.AddressInfoActivity
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.bean.HomeBean
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.view.GoodsView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import rx.Subscriber
import java.util.*


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
        class Sub : Subscriber<HomeBean>() {
            override fun onError(e: Throwable) {
            }

            override fun onNext(t: HomeBean) {
                setData(t.result!!.data!!)
            }


            override fun onCompleted() {
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
        val banner = rootview.findViewById<Banner>(R.id.banner)
        banner.setImages(images)
                .setImageLoader(GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(30000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

        //设置推荐商品
        val recommendList = data.yhcs!!.recommend as List<HomeBean.ResultBean.DataBean.YHCSBean.RecommendBean>
        for (i in 0 until recommendList.size) {
            val yhcs = recommendList[i]
            val goodsView = GoodsView(activity)
            val params = LinearLayout.LayoutParams(200, matchParent)
            goodsView.layoutParams = params
            goodsView.setGoods(yhcs.productname!!, yhcs.unit!!, yhcs.price!!, yhcs.img, 200)
            llGoods.addView(goodsView)
            goodsView.onClick {
                var intent = Intent(activity, GoodsDetailActivity::class.java)
                intent.putExtra("id", yhcs.id)
                LogUtils.d("id:" + yhcs.id)
                startActivity(intent)
            }
        }
    }
}
