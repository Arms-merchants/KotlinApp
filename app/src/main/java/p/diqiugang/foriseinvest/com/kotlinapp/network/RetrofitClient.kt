package p.diqiugang.foriseinvest.com.kotlinapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by heyueyang on 2017/6/27.
 */
enum class RetrofitClient{
    INSTANCE;
    val retrofit:Retrofit;

    constructor(){
        retrofit = Retrofit.Builder()
                .client(OKHttpFactory.INSTANCE.okHttpClient)
                .baseUrl("http://gank.io/api/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <T> create(service: Class<T>):T{
       return retrofit.create(service)
    }

}