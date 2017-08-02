package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

import p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.CardItem;
import p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.CardViewPage;

/**
 * Created by heyueyang on 2017/8/2.
 */

public class CardPagerAdapter extends FragmentStatePagerAdapter {

    private List<CardItem> mCardItems;
    private boolean mIsLoop;


    public CardPagerAdapter(FragmentManager fm, List<CardItem> cardItems, boolean isLoop) {
        super(fm);
        mCardItems = cardItems;
        mIsLoop = isLoop;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化item，FragmentStatePagerAdapter会在fragment销毁时保存它的状态，而且在它再次被创建时赋予它保存的状态
        //这里和FragmentPageAdapter不同的，FragmentPageAdapter不会，它是只会创建。这里如果是无限循环的话那么就要获取实际对应的item
        return super.instantiateItem(container, mIsLoop ? position % getRealCount() : position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mIsLoop) {
            CardViewPage viewPager = (CardViewPage) container;
            int pos = viewPager.getCurrentItem();
            int i = pos % getRealCount();
            int j = position % getRealCount();
            Logger.e("pos:" + pos + "-i:" + i + "-j:" + j);
            if (j >= i - 2 && j <= i + 2) {
                return;
            }
            super.destroyItem(container, j, object);
            return;
        }
        super.destroyItem(container, position, object);
    }


    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
        if (mIsLoop) {
            CardViewPage cardViewPage = (CardViewPage) container;
            int currentItem = cardViewPage.getCurrentItem();
            if (currentItem == 0) {
                currentItem = getFristItem();
            } else if (currentItem == getCount() - 1) {
                currentItem = getLastItem();
            }
            cardViewPage.setCurrentItem(currentItem,false);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return mCardItems.get(position);
    }

    @Override
    public int getCount() {
        return mIsLoop ? Integer.MAX_VALUE : mCardItems.size();
    }

    private int getRealCount() {
        return mCardItems == null ? 0 : mCardItems.size();
    }


    private int getFristItem() {
        int realCount = getRealCount();
        Logger.e("getFristItem:" + (Integer.MAX_VALUE / realCount / 2 * realCount));
        return Integer.MAX_VALUE / realCount / 2 * realCount;
    }

    private int getLastItem() {
        int realCount = getRealCount();
        Logger.e("getLastItem:" + (Integer.MAX_VALUE / realCount / 2 * realCount - 1));
        return Integer.MAX_VALUE / realCount / 2 * realCount - 1;
    }

}
