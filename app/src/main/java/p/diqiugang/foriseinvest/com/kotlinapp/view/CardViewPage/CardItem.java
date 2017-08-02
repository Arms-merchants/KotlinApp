package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by heyueyang on 2017/8/1.
 */

public class CardItem<T> extends BaseCardItem<T> {

    private T mData;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mHandler == null) {
            throw new RuntimeException("please bandle handler first！");
        }
        return mHandler.onBind(mContext, mData, mPosition);
    }

    public void bindData(T data, int position) {
        mData = data;
        mPosition = position;
    }


}
