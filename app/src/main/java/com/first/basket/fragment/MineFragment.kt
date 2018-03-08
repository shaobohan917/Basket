package com.first.basket.fragment

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.*
import com.first.basket.common.CommonMethod
import com.first.basket.common.GlideOptions
import com.first.basket.common.StaticValue
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 30/08/2017.
 */
class MineFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setLoginStatus()

        ImageUtils.showImg(activity, R.mipmap.ic_placeholder, ivAva, GlideOptions().circleCrop())
        ivMore.onClick {
            if (CommonMethod.isLogin()) {
                startActivity(Intent(activity, OrderListActivity::class.java))
            } else {
                CommonMethod.showLogin()
            }
        }

        ivAva.onClick {
            if(CommonMethod.isLogin()){

            }
        }

        tvLoginStatus.onClick {
            if (!SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
                var intent = Intent(activity, LoginActivity::class.java)
                startActivityForResult(intent, 102)
            } else {
                startActivityForResult(Intent(activity,ModifyNicknameActivity::class.java),104)
            }
        }

        aimvSetting.onClick {
            var intent = Intent(activity, SettingActivity::class.java)
            startActivityForResult(intent, 103)
        }

        aimvRange.onClick {
            startActivity(Intent(activity, RangeActivity::class.java))
        }

        aimvAbout.onClick {
            startActivity(Intent(activity, AboutActivity::class.java))
        }

        aimvAddress.onClick {
            if (CommonMethod.isLogin()) {
                startActivity(Intent(activity, AddressListActivity::class.java))
            } else {
                CommonMethod.showLogin()
            }
        }

        aimvHelp.onClick {
            (activity as MainActivity).showDialog("联系客服", activity.getString(R.string.service_phone), "拨打", DialogInterface.OnClickListener { p0, p1 ->
                val intent = Intent(Intent.ACTION_DIAL)
                val data = Uri.parse("tel:" + activity.getString(R.string.service_phone))
                intent.data = data
                startActivity(intent)
            })
        }

        tvRealName.onClick {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && (requestCode == 102 || requestCode == 103)|| requestCode == 104) {
            setLoginStatus()
        }
    }

    private fun setLoginStatus() {
        if (SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
            val phone = SPUtil.getString(StaticValue.SP_LOGIN_PHONE, "")
            val username = SPUtil.getString(StaticValue.SP_LOGIN_USERNAME, "")

            if(TextUtils.isEmpty(username)){
                tvLoginStatus.text = phone
            }else{
                tvLoginStatus.text = username
            }

            tvYBBL.text = "¥ "+SPUtil.getString(StaticValue.SP_INTEGRAL_YBBL, "")
            tvAXJJ.text = "¥ "+SPUtil.getString(StaticValue.SP_INTEGRAL_AXJJ, "")
            if(SPUtil.getInt(StaticValue.SP_ISREAL,0)==1){
                tvRealName.text = SPUtil.getString(StaticValue.SP_REALNAME, "")
            }else{
                tvRealName.text = "未实名"
            }

            tvRegion.visibility = View.VISIBLE
            tvRealName.visibility = View.VISIBLE
            llYBBL.visibility = View.VISIBLE
            ivAva.visibility = View.VISIBLE
        } else {
            tvLoginStatus.text = "登录/注册"

            tvRegion.visibility = View.GONE
            tvRealName.visibility = View.GONE
            llYBBL.visibility = View.GONE
            ivAva.visibility = View.INVISIBLE
        }
    }
}