package com.hazz.aipick.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.hazz.aipick.R
import com.hazz.aipick.base.BaseActivity
import com.hazz.aipick.mvp.contract.LoginContract
import com.hazz.aipick.mvp.presenter.RegistPresenter
import com.hazz.aipick.mvp.presenter.UserInfoPresenter
import com.hazz.aipick.utils.ToastUtils
import com.hazz.aipick.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_set_trade_pwd.*


class SetTradePwdActivity : BaseActivity(), LoginContract.RegistView, LoginContract.updateView {


    override fun updateSuccess(msg: String) {
        ToastUtils.showToast(this,  msg)
    }

    override fun onRegistSuccess(msg: String) {

    }

    override fun onSendMesSuccess(msg: String) {
        ToastUtils.showToast(this,  getString(R.string.mine_send_success))
        showCountDownView()
    }


    override fun layoutId(): Int = R.layout.activity_set_trade_pwd


    override fun initData() {

    }

    /**
     * 展示获取验证码倒计时
     */
    private fun showCountDownView() {
        tv_getCode.isEnabled = false
        tv_getCode.isClickable = false
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tv_getCode.isEnabled = true
                tv_getCode.isClickable = true
                tv_getCode.text = getString(R.string.get_verfycode)
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_getCode.text = "${millisUntilFinished / 1000}s"
            }
        }.start()

    }

    private var countDownTimer: CountDownTimer? = null
    private var mRegistPresenter: RegistPresenter = RegistPresenter(this)
    private var mUserInfoPresenter: UserInfoPresenter =UserInfoPresenter(this)

    private val REQUEST_AREACODE_CODE = 10005

    @SuppressLint("SetTextI18n")
    override fun initView() {
        ToolBarCustom.newBuilder(toolbar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.set_trade_pwd))
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBg(Color.parseColor("#1E2742"))
                .setOnLeftIconClickListener { finish() }

    }

    override fun start() {
        tv_quhao.setOnClickListener {
            startActivityForResult(Intent(this, CountryActivity::class.java), REQUEST_AREACODE_CODE)

        }

        tv_getCode.setOnClickListener {
            mRegistPresenter.sendCodeLogin("phone",tv_quhao.text.toString(),edit_phone.text.toString(),
                    "modify_trade_password"
                    )
        }

        bt_login.setOnClickListener {
            mUserInfoPresenter.update("add_trade","","",""
                    ,"",tv_quhao.text.toString(),edit_phone.text.toString(),"","",
                    "","",et_trade_pwd_again.text.toString())
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_AREACODE_CODE) {
            val areaCode = data?.getStringExtra("countryNumber") ?: "+86"
            tv_quhao.text = areaCode
        }
    }

}