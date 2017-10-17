package com.first.basket.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.activity.AddressInfoActivity
import com.first.basket.activity.SearchActivity
import com.first.basket.activity.WebViewActivity
import com.first.basket.base.BaseActivity
import com.first.basket.bean.HomeBean
import com.first.basket.common.StaticValue
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.collections.ArrayList


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
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        tvAddress.setText(SPUtil.getString(StaticValue.SP_ADDRESS, "  "))
    }

    private fun initData() {

        class Sub : HttpResultSubscriber<HomeBean>() {
            override fun onNext(t: HomeBean) {
                super.onNext(t)
                setData(t.result!!.data!!)
            }
        }

        HttpMethods.createService().getHome("get_mainpage")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(Sub())
    }

    private fun initListener() {
        var myClickListener = MyClickListener()

        llPosition.setOnClickListener {
            val intent = Intent(activity, AddressInfoActivity::class.java)
            startActivityForResult(intent, (activity as BaseActivity).REQUEST_ONE)
        }

        ivSearch.onClick {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        ivScan.onClick {
            //            startActivity(Intent(activity, MainActivity::class.java))
        }

        sqcs.setOnClickListener(myClickListener)
        qgcs.setOnClickListener(myClickListener)
        shcs.setOnClickListener(myClickListener)
    }

    inner class MyClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            when (view.id) {
                R.id.sqcs -> goClassify(1)
                R.id.shcs -> goClassify(2)
                R.id.qgcs -> goClassify(3)
            }
        }

        private fun goClassify(channel: Int) {
//            var intent = Intent(activity, ClassifyActivity::class.java)
//            intent.putExtra("channel", channel)
//            startActivity(intent)

            SPUtil.setData(activity, Constants.HOME_CLASSIFY, channel)
            activity.bottombar.selectTabAtPosition(1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == (activity as BaseActivity).REQUEST_ONE) {
//            val adds = data!!.getStringExtra("adds")
            val adds = SPUtil.getString(StaticValue.SP_ADDRESS, "")
            tvAddress.text = adds
        }
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
                .setBannerAnimation(Transformer.Default)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

        //设置垂直轮播
        for (i in 0 until data.sqcs.carouselfigure.size) {
            images1.add(Constants.BASE_IMG_URL + data.sqcs.carouselfigure[i].image)
        }
        vBanner.bindView(object : cn.ymex.banner.Banner.BindViewCallBack<AppCompatImageView, String> {
            override fun bindView(view: AppCompatImageView, data: String, position: Int) {
                view.scaleType = ImageView.ScaleType.FIT_CENTER
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
        ImageUtils.showImg(activity, data.hltg.image, ivHLTG)
        ImageUtils.showImg(activity, data.shcs.image, ivSHCS)
        ImageUtils.showImg(activity, data.qgcs.image, ivQGCS)
        ImageUtils.showImg(activity, data.jkss.image, ivJKSS)

        class OnIvClickListener : View.OnClickListener {
            var url = ""
            override fun onClick(view: View) {
                when (view.id) {
                    R.id.ivSHCS -> url = data.shcs.url
                    R.id.ivHLTG -> url = data.hltg.url
                    R.id.ivQGCS -> url = data.qgcs.url
                    R.id.ivJKSS -> url = data.jkss.url
                }
                var intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", url)
//                startActivity(intent)
            }
        }

        var onIvClickListener = OnIvClickListener()
        ivSHCS.setOnClickListener(onIvClickListener)
        ivHLTG.setOnClickListener(onIvClickListener)
        ivQGCS.setOnClickListener(onIvClickListener)
        ivJKSS.setOnClickListener(onIvClickListener)

    }

    fun setLocation(aoiName: String?) {
        tvAddress.text = aoiName
    }
}
