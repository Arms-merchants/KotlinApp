package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;


import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by heyueyang on 2017/8/1.
 */

public class BaseCardItem<T> extends Fragment {

    protected CardHandler<T> mHandler;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void bindHandle(CardHandler<T> handler) {
        mHandler = handler;
    }


}
