package p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import p.diqiugang.foriseinvest.com.kotlinapp.R;
import p.diqiugang.foriseinvest.com.kotlinapp.view.CardViewPage.adapter.CardPagerAdapter;

/**
 * Created by heyueyang on 2017/8/1.
 */

public class CardViewPage extends ViewPager {


    private int mMaxOffset;
    private boolean mIsLoop = false;

    /**
     * 播放时间
     */
    private int showTime = 3 * 1000;
    /**
     * 滚动方向
     */
    private Direction direction = Direction.LEFT;


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
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardViewPage);

        int padding = typedArray.getDimensionPixelOffset(R.styleable.CardViewPage_card_padding,
                //就是dp转换
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, displayMetrics));
        setPadding(getPaddingLeft() + padding, getPaddingTop(), getPaddingRight() + padding, getPaddingBottom());

        int margin = typedArray.getDimensionPixelOffset(R.styleable.CardViewPage_card_margin,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics));
        setPageMargin(margin);

        mMaxOffset = typedArray.getDimensionPixelOffset(R.styleable.CardViewPage_card_max_offset,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, displayMetrics));

        mIsLoop = typedArray.getBoolean(R.styleable.CardViewPage_card_loop, false);
        typedArray.recycle();
    }


    public <T> void onBind(FragmentManager fm, CardHandler<T> cardHandler, List<T> data) {
        List<CardItem> cardItems = getCardItems(cardHandler, data, mIsLoop);
        setPageTransformer(false, new CardViewPageTransformer(mMaxOffset));
        CardPagerAdapter cardPagerAdapter = new CardPagerAdapter(fm, cardItems, mIsLoop);

        setAdapter(cardPagerAdapter);
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
     * 设置播放时间，默认3秒
     *
     * @param showTimeMillis 毫秒
     */
    public void setShowTime(int showTimeMillis) {
        this.showTime = showTime;
    }

    /**
     * 设置滚动方向，默认向左滚动
     *
     * @param direction 方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }



    /**
     * 开始
     */
    public void start() {
        stop();
        postDelayed(player, showTime);
    }

    /**
     * 停止
     */
    public void stop() {
        removeCallbacks(player);
    }

    /**
     * 播放上一个
     */
    public void previous() {
        if (direction == Direction.RIGHT) {
            play(Direction.LEFT);
        } else if (direction == Direction.LEFT) {
            play(Direction.RIGHT);
        }
    }

    /**
     * 播放下一个
     */
    public void next() {
        play(direction);
    }

    /**
     * 执行播放
     *
     * @param direction 播放方向
     */
    private synchronized void play(Direction direction) {
        PagerAdapter pagerAdapter = getAdapter();
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            int currentItem = getCurrentItem();
            switch (direction) {
                case LEFT:
                    currentItem++;
                    if (currentItem > count)
                        currentItem = 0;
                    break;
                case RIGHT:
                    currentItem--;
                    if (currentItem < 0)
                        currentItem = count;
                    break;
            }
            setCurrentItem(currentItem);
        }
        start();
    }

    /**
     * 播放器
     */
    private Runnable player = new Runnable() {
        @Override
        public void run() {
            play(direction);
        }
    };

    public enum Direction {
        /**
         * 向左行动，播放的应该是右边的
         */
        LEFT,
        /**
         * 向右行动，播放的应该是左边的
         */
        RIGHT
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_IDLE)
                    start();
                else if (state == SCROLL_STATE_DRAGGING)
                    stop();
            }
        });
    }


}
