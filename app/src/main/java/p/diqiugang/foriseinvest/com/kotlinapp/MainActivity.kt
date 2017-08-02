package p.diqiugang.foriseinvest.com.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import p.diqiugang.foriseinvest.com.kotlinapp.adapter.ImageAdapter
import p.diqiugang.foriseinvest.com.kotlinapp.network.ApiService
import p.diqiugang.foriseinvest.com.kotlinapp.network.HttpCallBack
import p.diqiugang.foriseinvest.com.kotlinapp.view.TestActivity2
import java.util.*


class MainActivity : AppCompatActivity(), MainContact.View {

    var mAdapter: ImageAdapter = ImageAdapter(this, null)
    var page: Int = 1
    var datas: List<DataBean.ResultsBean> = ArrayList<DataBean.ResultsBean>()
    var mPresenter: MainContact.Presenter? = null
    var context: MainActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        EventBus.getDefault().register(this)

        /* tv.setOnClickListener(object : View.OnClickListener {
             override fun onClick(v: View?) {
                 ApiService().getData(1, object : HttpCallBack<DataBean> {
                     override fun onFail(throwable: Throwable?) {
                         Logger.e(throwable?.message)
                     }

                     override fun onSucess(t: DataBean?) {
                         t?.results?.forEach {
                             println(it.url)
                         }

                     }
                 })
             }
         })*/


        //lama表达式和上面的效果一样
        /* tv.setOnClickListener(View.OnClickListener {
             v ->
         })*/
        mPresenter = MainPresenter(this)
        mPresenter?.getData(page)
        rv.layoutManager = GridLayoutManager(this, 2)
        mAdapter?.openLoadAnimation()
        mAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                page++
                mPresenter?.getData(page)
            }
        }, rv)

        rv.adapter = mAdapter
        mAdapter?.setOnItemChildClickListener(object : BaseQuickAdapter.OnItemChildClickListener {
            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                intent = Intent(context, TestActivity2::class.java)
                context.startActivity(intent)
            }

        })

    }


    override fun setData(data: DataBean?) {
        when (data?.results?.size) {
            in 0..9 -> {
                mAdapter?.loadMoreEnd()
            }

            else -> {
                mAdapter?.loadMoreComplete()
            }
        }

        //mAdapter?.addData(data?.results)
        if (data != null) {
            mAdapter?.addData(data?.results)
        }

    }


    override fun getDataFail() {
        mAdapter?.loadMoreFail()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(str: String) {

    }


    /**
     * ButterKnife一样可以使用，不过因为是用anko引入布局文件了，不过点击事件什么的用这个还不错
     */
    /* @OnClick(R.id.tv)
     fun onClick(v: View) {
         Logger.e("onclick")
         when (v.id) {
             R.id.tv -> {
                 intent = Intent(this, TestActivity::class.java)
                 intent.putExtra("test", "111111")
                 startActivity(intent)
             }

         }
     }*/


    fun Test(a: Int, b: Int): Int {
        return a + b
    }

    fun Sum(a: Int, b: Int) = a + b

    /**
     * when 判断类型 Any任意类型 感觉和java中的switch很像
     * is和java中的instanceof 是一个作用判断是否为某个类型，!is判定不是这个类型
     */
    fun whenFun(obj: Any) {
        when (obj) {
            25 -> Toast.makeText(this, "25", Toast.LENGTH_LONG).show()
            is String -> Toast.makeText(this, "kotlin", Toast.LENGTH_LONG).show()
            !is Number -> Toast.makeText(this, "the number is not nmber", Toast.LENGTH_LONG).show()
            is Number -> if (obj.toInt() > 10) Toast.makeText(this, "this number is big 10", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this, "have nothing", Toast.LENGTH_LONG).show()
        }
    }

    fun forLoop(array: Array<String>) {
        //相当于forearch
        for (str in array) {
            println(str)
        }
        //kotlin 提供的forEach函数
        array.forEach {
            print(it)
        }
        //array.indices 数组索引
        for (i in array.indices) {
            println(array[i])
        }

        /**
         * var 变量 val 常量
         */
        var i = 0
        while (i < array.size) {
            println(array[i++])
        }

    }


    fun breakLoop() {
        size@ for (i in 0..2) {//for(int i =0;i<= 2;i++)的效果
            for (j in 0..3) {
                println("i:" + i + "--j:" + j)
                if (j == 2)
                    continue@size//跳到size层循环处，继续向下执行
                //break@size//跳到外层循环size处，跳出该层循环
            }
            if (i == 2)
                break
        }

        var test = Test("kotlin", 2, 11)
    }


    data class Test<T>(var name: String, var position: Int, var age: T)


    open class People(var id: String, var name: String) {
        var customName = name.toUpperCase()

        constructor(id: String, name: String, age: Int) : this(id, name) {
            println("constuctor")
        }

        init {
            println("初始化操作")
        }

        /**
         * open 能够被子类复写的方法
         */
        open fun study() {
            println("study")
        }

    }

    class Student(id: String, name: String) : People(id, name) {
        var test: Number = 3
        private var name1: String?
            get() {
                return name1
            }
            set(value) {
                name1 = value
            }

        override fun study() {
            super.study()

        }
    }


}
