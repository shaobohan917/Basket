package com.first.basket.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.activity.AddressListActivity
import com.first.basket.activity.MainActivity
import com.first.basket.activity.SearchActivity
import com.first.basket.activity.WebViewActivity
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.AddressBean
import com.first.basket.bean.HomeBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.GlideApp
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 30/08/2017.
 */
class HomeFragment : BaseFragment() {
    private var images = ArrayList<String>()
    private var images1 = ArrayList<String>()
    private var recommendData: HomeBean.DataBean? = HomeBean.DataBean()

    private lateinit var myClickListener: MyClickListener


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initListener()

    }

    private fun initView() {

    }

    private fun initData() {
        (activity as MainActivity).showLoading()
        HttpMethods.createService().getMainpage("get_mainpage")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<HomeBean>>() {
                    override fun onNext(t: HttpResult<HomeBean>) {
                        super.onNext(t)
                        setData(t.result.data)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        (activity as MainActivity).hideLoading()
                    }
                })
    }


    private fun initListener() {
        myClickListener = MyClickListener()

        llPosition.setOnClickListener {
            if (CommonMethod.isLogin()) {
                val intent = Intent(activity, AddressListActivity::class.java)
                startActivityForResult(intent, (activity as BaseActivity).REQUEST_ONE)
            } else {
                (activity as MainActivity).showLogin()
            }

        }

        ivSearch.onClick {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        ivScan.onClick {
            //            startActivity(Intent(activity, DecoderActivity::class.java))
        }

        sqcs.setOnClickListener(myClickListener)
        axwx.setOnClickListener(myClickListener)
        shcs.setOnClickListener(myClickListener)
        hltg.setOnClickListener(myClickListener)
        ybbl.setOnClickListener(myClickListener)
    }

    inner class MyClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            when (view.id) {
                R.id.sqcs -> {
                    goClassify(1)
                }
                R.id.shcs, R.id.ivSHCS -> goClassify(2)
                R.id.hltg, R.id.ivHLTG -> {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("title", "欢乐团购")
                    intent.putExtra("url", recommendData?.hltg?.url)
                    startActivity(intent)
                }
                R.id.axwx, R.id.ivJKSS -> {
//                    val intent = Intent(activity, WebViewActivity::class.java)
//                    intent.putExtra("url", recommendData?.jkss?.url)
//                    intent.putExtra("title", "爱心无限")
//                    startActivity(intent)
                }
                R.id.ybbl->{
//                    val intent = Intent(activity, WebViewActivity::class.java)
//                    intent.putExtra("url", recommendData?.jkss?.url)
//                    intent.putExtra("title", "医保伴侣")
//                    startActivity(intent)
                }
            }
        }
    }


    private fun goClassify(channel: Int) {
        (activity as MainActivity).mChannel = channel
        activity.bottombar.selectTabAtPosition(1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == (activity as BaseActivity).REQUEST_ONE) {
            val addressInfo = data?.getSerializableExtra("addressInfo") as AddressBean
            tvAddress.text = addressInfo.street.replace("&", " ")
        }
    }


    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            ImageUtils.showImg(context, path.toString(),imageView)
        }
    }


    private fun setData(data: HomeBean.DataBean) {
        //设置banner
        (0 until data.carouselfigure.size)
                .map { Constants.BASE_IMG_URL + data.carouselfigure[it].image }
                .forEach { images.add(it) }
        banner.setImages(images)
                .setImageLoader(GlideImageLoader())
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()
        banner.setOnBannerListener {
            if (!TextUtils.isEmpty(data.carouselfigure[0].url)) {
                var intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", data.carouselfigure[0].url)
                startActivity(intent)
            }
        }

        //设置垂直轮播
        for (i in 0 until data.sqcs.carouselfigure.size) {
            images1.add(Constants.BASE_IMG_URL + data.sqcs.carouselfigure[i].image)
        }
        vBanner.setImages(images1)
                .setImageLoader(GlideImageLoader())
                .setBannerAnimation(Transformer.ScaleInOut)
                .setDelayTime(5000)
                .start()

//        vBanner.bindView(object : cn.ymex.banner.Banner.BindViewCallBack<AppCompatImageView, String> {
//            override fun bindView(view: AppCompatImageView, data: String, position: Int) {
//                view.scaleType = ImageView.ScaleType.FIT_CENTER
//                //图片加载
//                Glide.with(view.context)
//                        .load(data)
//                        .into(view)
//                view.onClick {
//                    goClassify(1)
//                }
//            }
//        }).setOrientation(cn.ymex.banner.Banner.VERTICAL).execute(images1)

        ImageUtils.showImg(activity, data.sqcs.vegetables, vegetables)
        ImageUtils.showImg(activity, data.sqcs.meat, meat)

        //设置推荐商品
        setRecommendData(data)
    }


    private fun setRecommendData(data: HomeBean.DataBean?) {
        this.recommendData = data
        ImageUtils.showImg(activity, data?.hltg?.image, ivHLTG)
        ImageUtils.showImg(activity, data?.shcs?.image, ivSHCS)
        ImageUtils.showImg(activity, data?.qgcs?.image, ivQGCS)
        ImageUtils.showImg(activity, data?.jkss?.image, ivJKSS)

        ivSHCS.setOnClickListener(myClickListener)
        ivHLTG.setOnClickListener(myClickListener)
        ivQGCS.setOnClickListener(myClickListener)
        ivJKSS.setOnClickListener(myClickListener)

    }

    fun setLocation(aoiName: String?) {
        tvAddress.text = aoiName
    }
}
