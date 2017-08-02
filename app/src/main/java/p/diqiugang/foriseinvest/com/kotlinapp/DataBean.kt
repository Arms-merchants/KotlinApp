package p.diqiugang.foriseinvest.com.kotlinapp

/**
 * Created by heyueyang on 2017/6/27.
 */


data class DataBean(val error: Boolean, val results: List<ResultsBean>) {

    inner class ResultsBean(val _id: String,
                            val createdAt: String,
                            val desc: String,
                            val images: String,
                            val publishedAt: String,
                            val source: String,
                            val type: String,
                            val url: String,
                            val used: Boolean,
                            val who: String)
}

