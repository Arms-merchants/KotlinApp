package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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

    }
}
