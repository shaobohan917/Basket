package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import butterknife.ButterKnife
import com.first.basket.base.BaseActivity
import com.first.basket.R
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.constants.Constants
import com.first.basket.fragment.HomeFragment
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.ToastUtil
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import rx.Subscriber

/**
 * Created by hanshaobo on 02/09/2017.
 */
class GoodsDetailActivity : BaseActivity() {
    private lateinit var data: GoodsDetailBean.ResultBean.DataBean

    private val picUrl = Constants.PIC_URL
    private var images = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)
        setContentView(R.layout.activity_detail)
        initView()
        getData(intent.extras.getString("id"))
        initListener()
    }

    private fun initView() {
        images.add(picUrl)
        banner.setImages(images)
                .setImageLoader(HomeFragment.GlideImageLoader())
                .setBannerAnimation(Transformer.DepthPage)
                .setDelayTime(30000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start()

    }

    private fun initListener() {
        btAdd.onClick {
            ToastUtil.showToast(this@GoodsDetailActivity, "已加入购物车")
        }
        ivShop.onClick {
            intent.putExtra("goods", data)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    private fun getData(id: String) {
        HttpMethods.createService().getDetail("get_productdetailpage", id)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<GoodsDetailBean>() {
                    override fun onNext(t: GoodsDetailBean) {
                        val data = t.result!!.data
                        setData(data!!)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }


    private fun setData(data: GoodsDetailBean.ResultBean.DataBean) {
        this.data = data
        tvName.text = data.title as CharSequence?
        tvDes.text = data.subtitle.toString()
        tvPrice.text = getString(R.string.price, data.price)
        tvDetail.text = data.productdetail ?: ""
    }


}