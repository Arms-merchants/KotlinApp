package p.diqiugang.foriseinvest.com.kotlinapp.network

/**
 * Created by heyueyang on 2017/6/28.
 */

interface HttpCallBack<T> {

    fun onSucess(t: T?)

    fun onFail(throwable: Throwable?)
}