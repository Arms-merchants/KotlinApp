package p.diqiugang.foriseinvest.com.kotlinapp.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import p.diqiugang.foriseinvest.com.kotlinapp.DataBean
import p.diqiugang.foriseinvest.com.kotlinapp.R

/**
 * Created by heyueyang on 2017/6/28.
 */
class ImageAdapter : BaseQuickAdapter<DataBean.ResultsBean, BaseViewHolder> {
    var context: Context? = null

    constructor(context: Context, data: List<DataBean.ResultsBean>?) : super(R.layout.item_rv, data) {
        this.context = context
    }


    override fun convert(helper: BaseViewHolder?, item: DataBean.ResultsBean?) {

        if (helper?.adapterPosition == 2) {
            var imageView = helper.getView<ImageView>(R.id.iv)
            Glide.with(context).load(R.drawable.ic_adb_black_24dp).into(imageView)

        } else {
            Glide.with(context).load(item?.url).into(helper?.getView(R.id.iv))
        }
        helper?.addOnClickListener(R.id.iv)

    }
}