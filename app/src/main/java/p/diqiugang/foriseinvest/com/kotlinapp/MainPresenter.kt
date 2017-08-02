package p.diqiugang.foriseinvest.com.kotlinapp

import com.orhanobut.logger.Logger
import p.diqiugang.foriseinvest.com.kotlinapp.network.ApiService
import p.diqiugang.foriseinvest.com.kotlinapp.network.HttpCallBack


/**
 * Created by heyueyang on 2017/6/28.
 */
class MainPresenter : MainContact.Presenter {
    //var 变量 val常量
    var mView: MainContact.View? = null
    var apiService: ApiService? = null

    constructor(view: MainContact.View) {
        mView = view
        apiService = ApiService()
    }


    override fun getData(page: Int) {
        Logger.e("getdata")
        apiService?.getData(page, object : HttpCallBack<DataBean> {
            override fun onFail(throwable: Throwable?) {
                Logger.e(throwable?.message)
                mView?.getDataFail()
            }

            override fun onSucess(t: DataBean?) {
                mView?.setData(t)
            }
        })
    }
}