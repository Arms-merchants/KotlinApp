package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * Created by heyueyang on 2017/8/1.
 * viewpage实现切换效果的实现类
 */

public class CardViewPageTransformer implements ViewPager.PageTransformer {

    private int mMaxTranslateOffsetX;//最大的偏移量
    private ViewPager mViewPager;

    public CardViewPageTransformer(int mMaxTranslateOffsetX) {
        this.mMaxTranslateOffsetX = mMaxTranslateOffsetX;
    }


    /**
     *     if (mViewPager == null) {
     mViewPager = (ViewPager) page.getParent();
     }
     int leftInScreen = page.getLeft() - mViewPager.getScrollX();
     int centerXInViewPager = leftInScreen + page.getMeasuredWidth() / 2;
     int offsetX = centerXInViewPager - mViewPager.getMeasuredWidth() / 2;
     float offsetRate = (float) offsetX * 0.38f / mViewPager.getMeasuredWidth();
     float scaleFactor = 1 - Math.abs(offsetRate);
     if (scaleFactor > 0) {
     page.setScaleX(scaleFactor);
     page.setScaleY(scaleFactor);
     page.setTranslationX(-mMaxTranslateOffsetX * offsetRate);
     }
     */


    /**
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        if (mViewPager == null) {
            mViewPager = (ViewPager) page.getParent();
        }
        //计算view的左边在屏幕中的位置
        int leftInScreen = page.getLeft() - mViewPager.getScrollX();
        Logger.e("mViewPage scrollX:" + mViewPager.getScrollX());
        int centerXInViewPage = leftInScreen + page.getMeasuredWidth() / 2;
        //计算view的偏移量
        /**
         * 这里如果view没有设置padding的话leftInScreen和offSetX是相同的，但如果有padding值，那么就这一半的宽度就不一样了
         */

        int offsetX = centerXInViewPage - mViewPager.getMeasuredWidth() / 2;
        //viewpage的宽度和偏移量的比值，为了后面作缩放效果提供一个缩放比例，这里因为offsetX会出现负值
        float offsetRate = (float) offsetX * 0.38f / mViewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);

        /**
         * 这里要注意，当view出去的时候，这个view是缩放，那么他们之间的间距会变大，所以为了间距不变，那么要把这个view向相反的方向移动
         */

        if (scaleFactor > 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setTranslationX(-mMaxTranslateOffsetX * offsetRate);
        }

    }
}
