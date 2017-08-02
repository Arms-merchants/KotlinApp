package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import p.diqiugang.foriseinvest.com.kotlinapp.R;

/**
 * Created by heyueyang on 2017/8/1.
 */

public class CardViewPage extends ViewPager {


    private int mMaxOffset;
    private boolean mIsLoop = false;

    public CardViewPage(Context context) {
        this(context, null);
    }

    public CardViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
            设置一些基本属性 padding margin 和最大的偏移量，是否无限循环
         */
        //设置viewpage不会裁剪超出view的部分
        setClipToPadding(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardViewPage);

        int padding = typedArray.getDimensionPixelSize(R.styleable.CardViewPage_card_padding,
                //就是dp转换
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics));
        setPadding(getLeft() + padding, getTop(), getRight() + padding, getBottom());

        int margin = typedArray.getDimensionPixelSize(R.styleable.CardViewPage_card_margin,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics));
        setPageMargin(margin);

        mMaxOffset = typedArray.getDimensionPixelSize(R.styleable.CardViewPage_card_max_offset,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, displayMetrics));

        mIsLoop = typedArray.getBoolean(R.styleable.CardViewPage_card_loop, false);
        typedArray.recycle();
    }

    /**
     * @param cardHandler
     * @param data
     * @param isLoop
     * @param <T>
     * @return
     */
    private <T> List<CardItem> getCardItems(CardHandler<T> cardHandler, List<T> data, boolean isLoop) {
        //把传进来的数据格式话为CardItem
        ArrayList<CardItem> cardItems = new ArrayList<>();
        int dataSize = data.size();
        //如果是无限循环的话并且数据小于6那么就需要做数据扩展
        boolean isExplan = isLoop && dataSize < 6;
        int radio = 6 / dataSize < 2 ? 2 : 6 / dataSize;
        int size = isExplan ? dataSize * radio : dataSize;
        for (int i = 0; i < size; i++) {
            //获取数据
            int position = isExplan ? i % dataSize : i;
            T t = data.get(position);
            CardItem item = new CardItem();
            item.bindHandle(cardHandler);
            item.bindData(t, position);
            cardItems.add(item);
        }
        return cardItems;
    }


    /**
     *  int position = isExpand ? i % dataSize : i;
     T t = data.get(position);
     CardItem<T> item = new CardItem<T>();
     item.bindHandler(handler);
     item.bindData(t, position);
     cardItems.add(item);
     */

}
