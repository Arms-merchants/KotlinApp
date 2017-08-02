package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.app.Fragment;
import android.content.Context;

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

}
