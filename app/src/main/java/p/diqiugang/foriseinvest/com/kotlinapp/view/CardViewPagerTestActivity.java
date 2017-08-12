package p.diqiugang.foriseinvest.com.kotlinapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import p.diqiugang.foriseinvest.com.kotlinapp.R;
import p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.CardHandler;
import p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.CardViewPage;
import p.diqiugang.foriseinvest.com.paletteimageview.PaletteImageView;


/**
 * Created by heyueyang on 2017/8/3.
 */

public class CardViewPagerTestActivity extends AppCompatActivity {

    public static final String DATA = "data";

    @BindView(R.id.card_view_page)
    CardViewPage cardViewPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ButterKnife.bind(this);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra(DATA);
        cardViewPage.onBind(getSupportFragmentManager(), new MyCandle(), stringArrayListExtra);
        cardViewPage.start();

    }

    class MyCandle implements CardHandler<String> {

        @Override
        public View onBind(Context context, String data, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_view, null);
            final PaletteImageView paletteImageView = (PaletteImageView) view.findViewById(R.id.palette_image_view);
            Glide.with(context).asBitmap().load(data).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if (resource != null) {
                        paletteImageView.setmBitmap(resource);
                    }
                }
            });
            return view;
        }


    }


}

