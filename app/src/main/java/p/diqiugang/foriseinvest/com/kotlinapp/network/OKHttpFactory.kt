package p.diqiugang.foriseinvest.com.kotlinapp.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit;

/**
 * Created by heyueyang on 2017/6/27.
 */
enum class OKHttpFactory{
    INSTANCE;
    var okHttpClient:OkHttpClient

    val TIMEOUT_READ = 30L
    val  TIMEOUT_WRITE = 30L
    val TIMEOUT_CONNECTION = 30L

    constructor(){

        var builder =  OkHttpClient.Builder()
        builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
        builder.writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
        builder.connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
        okHttpClient = builder.build()
    }
}