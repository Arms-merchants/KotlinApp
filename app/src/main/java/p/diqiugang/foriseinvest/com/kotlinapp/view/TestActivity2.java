package p.diqiugang.foriseinvest.com.kotlinapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import p.diqiugang.foriseinvest.com.kotlinapp.R;
import p.diqiugang.foriseinvest.com.paletteimageview.PaletteImageView;

/**
 * Created by heyueyang on 2017/7/28.
 */

public class TestActivity2 extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.palette_image_view)
    PaletteImageView paletteImageView;
    @BindView(R.id.seekbar1)
    SeekBar seekbar1;
    @BindView(R.id.seekbar2)
    SeekBar seekbar2;
    @BindView(R.id.seekbar3)
    SeekBar seekbar3;
    @BindView(R.id.seekbar4)
    SeekBar seekbar4;
    private ArrayList<String> stringArrayListExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        seekbar1.setOnSeekBarChangeListener(this);
        seekbar2.setOnSeekBarChangeListener(this);
        seekbar3.setOnSeekBarChangeListener(this);
        seekbar4.setOnSeekBarChangeListener(this);
        stringArrayListExtra = getIntent().getStringArrayListExtra(CardViewPagerTestActivity.DATA);
        Glide.with(this).asBitmap().load(stringArrayListExtra.get(0)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                paletteImageView.setmBitmap(resource);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        handleProgress(seekBar, progress);
    }


    @OnClick(R.id.palette_image_view)
    public void onClick(View view) {
        Intent intent = new Intent(this, CardViewPagerTestActivity.class);
        intent.putExtra(CardViewPagerTestActivity.DATA, stringArrayListExtra);
        startActivity(intent);
    }

    private void handleProgress(SeekBar seekBar, int progress) {
        switch (seekBar.getId()) {
            case R.id.seekbar1:
                paletteImageView.setPaletteRadiu(progress);
                break;
            case R.id.seekbar2:
                paletteImageView.setPaletteShadowRadiu(progress);
                break;
            case R.id.seekbar3:
                paletteImageView.setPaletteShadowOffset(progress, 0);
                break;
            case R.id.seekbar4:
                paletteImageView.setPaletteShadowOffset(0, progress);
                break;
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
