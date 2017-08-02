package p.diqiugang.foriseinvest.com.kotlinapp.network

import com.orhanobut.logger.Logger
import p.diqiugang.foriseinvest.com.kotlinapp.DataBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by heyueyang on 2017/6/27.
 */
class ApiService {


    fun getData(page: Int, callBack: HttpCallBack<DataBean>) {
        Logger.e("getdata---")
        RetrofitClient.INSTANCE
                .create(Api::class.java)
                .getData(page)
                .enqueue(object : Callback<DataBean> {
                    override fun onResponse(call: Call<DataBean>?, response: Response<DataBean>?) {
                        //response?.body() response不为null的时候才会执行。。。
                        Logger.e("onResponse---")
                        callBack.onSucess(response?.body())
                    }

                    override fun onFailure(call: Call<DataBean>?, t: Throwable?) {
                        callBack.onFail(t)
                    }

                })
    }

}