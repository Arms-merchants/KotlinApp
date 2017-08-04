package p.diqiugang.foriseinvest.com.kotlinapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

import p.diqiugang.foriseinvest.com.kotlinapp.R;

/**
 * Created by heyueyang on 2017/7/25.
 * <p>
 * 绘制一个带有阴影效果的view  阴影的效果随着图片的主色调设置，并且能够设置圆角和偏移量
 * 使用Paletter获取图片的主色调
 */

public class PaletteImageView extends View {


    private static final int MSG = 0x101;
    private static final int DEFAULT_PADDING = 40;
    private static final int DEFAULT_OFFSET = 20;
    private static final int DEFAULT_SHADOW_RADIUS = 20;
    private Paint mPaintShadow;
    private Paint mPaint;
    private int mRadius;
    private int mPadding;
    private Bitmap mBitmap;
    private int mImgId;
    private AsyncTask mAsyncTask;
    public int mMainColor = -1;
    private int mOffsetX = DEFAULT_OFFSET;
    private int mOffsetY = DEFAULT_OFFSET;
    private int mShadowRadius = DEFAULT_SHADOW_RADIUS;
    private Palette mPalette;
    private RectF mRectFShadow;
    private Bitmap mRealBitmap;
    private int mOnMeasureHeightMode = -1;
    public PaletteImageView mInstance;
    private Bitmap mRoundBitmap;
    private RectF mRoundRectF;
    private PorterDuffXfermode mPorterDuffXfermode; //类主要用于图形合成时的图像过渡模式计算
    private OnParseColorListener mListener;
    private Handler mHandler;

    public PaletteImageView(Context context) {
        this(context, null);
    }

    public PaletteImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaletteImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mInstance = this;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PaletteImageView);
        mOffsetX = a.getDimensionPixelOffset(R.styleable.PaletteImageView_paletteOffsetX, DEFAULT_OFFSET);
        mOffsetY = a.getDimensionPixelOffset(R.styleable.PaletteImageView_paletteOffsetY, DEFAULT_OFFSET);
        mRadius = a.getDimensionPixelOffset(R.styleable.PaletteImageView_paletteRadius, 0);
        mImgId = a.getResourceId(R.styleable.PaletteImageView_paletteSrc, 0);
        mPadding = a.getDimensionPixelOffset(R.styleable.PaletteImageView_palettePadding, 0);
        mShadowRadius = a.getDimensionPixelSize(R.styleable.PaletteImageView_palettesShadowRadius, DEFAULT_SHADOW_RADIUS);
        a.recycle();

        setPadding(mPadding, mPadding, mPadding, mPadding);
        //构建画笔 去锯齿
        mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置防抖
        mPaintShadow.setDither(true);

        /**
         * 硬件加速 是使用view layers(硬件层) ，将view渲染入一个非屏幕区域的缓冲区，并根据需要操控它，这个功能主要是针对动画，因为它能让复杂动画效果更加的流畅。如果不使用
         * 硬件层的话，view会在动画属性改变之后进行一次刷新，而对相对复杂的view，这一次的刷新又会连带它的子view进行刷新，并且进行重新绘制，相当的耗费性能。使用view layers
         * 通过调用硬件层，GPU直接为我们的view创建一个结构，并且不会造成view的刷新。而我们可以在避免刷新的情况下对这个接口进行多种的操作，比如x/y位置变换，旋转，透明度等等
         * ，这意味着我们可以对一个让一个复杂的view执行动画的同时，又不会刷新。但是硬件加速是会在GPU单独开辟一块被占用的内存，那么我们要在使用后释放掉它
         * 主动释放 view.setLayerType(View.LAYER_TYPE_NONE,null)
         * 或者使用withLayer（）
         * view.animate().ranslationX(20f).withLayer().start();  这会在动画开始的的时候自动创建硬件层并且在结束的时候自动移除
         *
         * LAYER_TYPE_SOFTWARE
         * 无论硬件加速是否打开，都会有一张bitmap，并在上面进行软渲染
         * 好处：在进行动画时，使用software可以只画一次view树，很省。 但如果view树经常更新时不要用，尤其是在硬件加速打开时使用，每次更新消耗的时间更多，因为渲染完这张bitmap
         *
         * LAYER_TYPE_HARDWARE
         硬件加速关闭时，作用同software。
         硬件加速打开时会在FBO（Framebuffer Object）上面做渲染，在进行动画时，View树也只需要画一次。


         两者区别：
         1、一个是渲染到Bitmap，一个是渲染到FB上。
         2、hardware可能会有一些操作不支持。
         两者相同：
         都是开了一个buffer，把View画到这个buffer上面去。


         LAYER_TYPE_NONE
         这个就比较简单了，不为这个View树建立单独的layer
         *
         */

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//在两者相交的地方绘制源图像，并且绘制的效果会受到目标图像对应地方透明度的影响
        //这个模式就是只绘制目标和源图像交汇的地方，并且是目标图像
        mHandler = new MyHandler(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        mOnMeasureHeightMode = mode;
        if (mOnMeasureHeightMode == MeasureSpec.UNSPECIFIED) {
            //如果控件为指定高度时，
            if (mBitmap != null) {
                height = (width - 2 * mPadding) * (mBitmap.getHeight() / mBitmap.getWidth()) + 2 * mPadding;
            }

            if (mRealBitmap != null) {
                height = mRealBitmap.getHeight() + 2 * mPadding;
            }
        }

        if (mBitmap != null) {
            height = (width - 2 * mPadding) * (mBitmap.getHeight() / mBitmap.getWidth()) + 2 * mPadding;
        }

        setMeasuredDimension(width, height);
    }


    /**
     * 获取bitmap，并确定它的的主色调
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        zipBitmap(mImgId, mBitmap, mOnMeasureHeightMode);
        //  矩阵两个坐标点，左上，右下
        //阴影的大小矩阵，这个可以理解就是图片的大小矩阵
        mRectFShadow = new RectF(mPadding, mPadding, getWidth() - mPadding, getHeight() - mPadding);
        //圆角图片，也就是我们真实的图片所处的矩阵范围
        mRoundRectF = new RectF(0, 0, getWidth() - mPadding * 2, getHeight() - 2 * mPadding);
        mRoundBitmap = creatRoundConerBitmap(mRealBitmap, mRadius);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRealBitmap != null) {
            //绘制底层的阴影
            canvas.drawRoundRect(mRectFShadow, mRadius, mRadius, mPaintShadow);
            //绘制表层的图片
            canvas.drawBitmap(mRoundBitmap, mPadding, mPadding, mPaint);
            //取消颜色解析的任务
            if (mMainColor != -1) {
                mAsyncTask.cancel(true);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //在从窗口接触绑定的时候取消所有的回掉
        mHandler.removeCallbacksAndMessages(null);
    }


    /**
     * 设置阴影颜色
     */
    public void setShadowColor(int color) {
        this.mMainColor = color;
        mHandler.sendEmptyMessage(MSG);
    }

    public void setmBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        mImgId = 0;
        mHandler.sendEmptyMessage(MSG);
    }

    /**
     * 设置图片的圆角的半径
     *
     * @param raius
     */
    public void setPaletteRadiu(int raius) {
        this.mRadius = raius;
        mRoundBitmap = creatRoundConerBitmap(mRealBitmap, mRadius);
        invalidate();
    }


    /**
     * 设置偏移量,最大的偏移量为padding值
     */
    public void setPaletteShadowOffset(int offsetX, int offsetY) {
        if (offsetX >= mPadding) {
            this.mOffsetX = mPadding;
        } else {
            this.mOffsetX = offsetX;
        }

        if (offsetY >= mPadding) {
            this.mOffsetY = mPadding;
        } else {
            this.mOffsetY = offsetY;
        }

        //发送消息重新绘制图像
        mHandler.sendEmptyMessage(MSG);
    }

    public void setPaletteShadowRadiu(int radiu) {
        this.mShadowRadius = radiu;
        mHandler.sendEmptyMessage(MSG);
    }

    public void setOnParseColorListener(OnParseColorListener listener) {
        this.mListener = listener;
    }


    /**
     * 图片压缩
     *
     * @param imgId
     * @param bitmap
     * @param heightNode 高的模式
     */
    private void zipBitmap(int imgId, Bitmap bitmap, int heightNode) {
        WeakReference<Matrix> matrixWeakReference = new WeakReference<Matrix>(new Matrix());
        if (matrixWeakReference.get() == null) return;
        Matrix matrix = matrixWeakReference.get();
        int reqWidth = getWidth() - mPadding - mPadding;
        int reqHeight = getHeight() - (2 * mPadding);
        if (reqWidth <= 0 || reqHeight <= 0) {
            return;
        }
        int rawWidth = 0;
        int rawHeight = 0;
        if (imgId != 0 && bitmap == null) {
            //通过xml设置资源id设置的图片
            WeakReference<BitmapFactory.Options> weakOptions = new WeakReference<BitmapFactory.Options>(new BitmapFactory.Options());
            if (weakOptions.get() == null) {
                return;
            }
            BitmapFactory.Options options = weakOptions.get();
            BitmapFactory.decodeResource(getResources(), imgId, options);
            options.inJustDecodeBounds = true;
            rawHeight = options.outHeight;
            rawWidth = options.outWidth;

            options.inSampleSize = calculateInSampleSize(rawWidth, rawHeight, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(getResources(), mImgId, options);
        } else if (imgId == 0 && bitmap != null) {
            //通过设置bitmap设置图片
            rawWidth = bitmap.getWidth();
            rawHeight = bitmap.getHeight();
            float scale = rawWidth / rawHeight;
            mRealBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) (rawWidth * scale), true);
            intShadow(mRealBitmap);
            return;
        }

        if (heightNode == MeasureSpec.UNSPECIFIED) {//如果高的模式是没有指定的
            float scale = rawHeight * 1.0f / rawWidth;
            mRealBitmap = Bitmap.createScaledBitmap(bitmap, reqWidth, (int) (reqWidth * scale), true);
        } else {
            int small = Math.min(rawWidth, rawHeight);
            int big = Math.max(reqHeight, reqWidth);
            float scale = big * 1.0f / small;
            matrix.setScale(scale, scale);
            mRealBitmap = Bitmap.createBitmap(bitmap, 0, 0, small, small, matrix, true);
        }

        intShadow(mRealBitmap);
    }


    /**
     * 获取bitmap的主色调
     *
     * @param bitmap
     */
    private void intShadow(Bitmap bitmap) {
        if (bitmap != null) {
            //解析图片颜色异步的一个任务
            mAsyncTask = Palette.from(bitmap).generate(paletteAsyncListener);
        }
    }

    /**
     * Palette的异步回掉，这里可以获取到图片颜色的主色调
     */
    private Palette.PaletteAsyncListener paletteAsyncListener = new Palette.PaletteAsyncListener() {
        @Override
        public void onGenerated(Palette palette) {
            if (palette != null) {
                mPalette = palette;
                //图片的主色调
                mMainColor = palette.getDominantSwatch().getRgb();
                mHandler.sendEmptyMessage(MSG);
                if (mListener != null) {
                    mListener.onComplete(mInstance);
                }
            } else {
                if (mListener != null) {
                    mListener.onFail();
                }
            }
        }
    };


    /**
     * 创建圆角图片
     *
     * @param bitmap
     * @param radius 圆角半径
     * @return
     */
    private Bitmap creatRoundConerBitmap(Bitmap bitmap, int radius) {
        Bitmap target = Bitmap.createBitmap(getWidth() - 2 * mPadding, getHeight() - 2 * mPadding, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(target);
        /**
         *  rx：x方向上的圆角半径。

         ry：y方向上的圆角半径。
         */
        canvas.drawRoundRect(mRoundRectF, radius, radius, mPaint);
        //设置混合模式
        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        return target;
    }


    /**
     * 获取压缩比例
     *
     * @param rawWidth
     * @param rawHeight
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int calculateInSampleSize(int rawWidth, int rawHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (rawHeight > reqHeight || rawWidth > reqWidth) {
            int halfHeight = rawHeight / 2;
            int halfWidth = rawWidth / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public interface OnParseColorListener {
        void onComplete(PaletteImageView paletteImageView);

        void onFail();
    }

    public static class MyHandler extends Handler {
        //弱引用
        private final WeakReference<PaletteImageView> paletteImageViewWeakReference;

        public MyHandler(PaletteImageView paletteImageView) {
            paletteImageViewWeakReference = new WeakReference<PaletteImageView>(paletteImageView);
        }

        @Override
        public void handleMessage(Message msg) {
            if (paletteImageViewWeakReference.get() != null) {
                PaletteImageView paletteImageView = paletteImageViewWeakReference.get();
                if (paletteImageView.mOffsetX < DEFAULT_OFFSET)
                    paletteImageView.mOffsetX = DEFAULT_OFFSET;
                if (paletteImageView.mOffsetY < DEFAULT_OFFSET)
                    paletteImageView.mOffsetY = DEFAULT_OFFSET;
                if (paletteImageView.mShadowRadius < DEFAULT_SHADOW_RADIUS)
                    paletteImageView.mShadowRadius = DEFAULT_SHADOW_RADIUS;
                /** 设置阴影效果
                 *  public   void   setShadowLayer (float radius, float dx, float dy, int color)
                 radius：阴影半径
                 dx：X轴方向的偏移量
                 dy：Y轴方向的偏移量
                 color：阴影颜色
                 */
                //给绘制阴影的画笔设置颜色
                paletteImageView.mPaintShadow.setShadowLayer(paletteImageView.mShadowRadius, paletteImageView.mOffsetX, paletteImageView.mOffsetY, paletteImageView.mMainColor);
                paletteImageView.invalidate();
            }
        }
    }


}
