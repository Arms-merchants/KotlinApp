package p.diqiugang.foriseinvest.com.kotlinapp.network


import p.diqiugang.foriseinvest.com.kotlinapp.DataBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by heyueyang on 2017/6/27.
 */
interface Api {

    @GET("福利/10/{page}")
    fun getData(@Path("page") page: Int): Call<DataBean>

}