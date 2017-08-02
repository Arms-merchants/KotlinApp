package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.content.Context;
import android.view.View;

/**
 * Created by heyueyang on 2017/8/1.
 * 实际进行view设置的地方
 */

public interface CardHandler<T> {
    View onBind(Context context, T data, int position);
}
