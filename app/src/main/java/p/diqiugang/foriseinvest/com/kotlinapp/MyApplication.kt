package p.diqiugang.foriseinvest.com.kotlinapp

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by heyueyang on 2017/6/27.
 */

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}
