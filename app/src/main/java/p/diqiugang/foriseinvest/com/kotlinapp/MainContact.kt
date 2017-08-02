package p.diqiugang.foriseinvest.com.kotlinapp

/**
 * Created by heyueyang on 2017/6/28.
 */
interface MainContact {
    interface View {
        fun setData(data: DataBean?)
        fun getDataFail()
    }

    interface Presenter {
        fun getData(page: Int)
    }

}