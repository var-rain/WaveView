package com.var.wave.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.var.wave.R;

/**
 * Created by: var_rain.
 * Created date: 2018/10/22.
 * Description: 自定义波浪View
 */
public class WaveView extends View {

    /* 波浪范围 */
    private int waveSize;
    /* 波浪颜色 */
    private int waveColor;
    /* 填充半径 */
    private int fillRadius;
    /* 反向波浪 */
    private boolean revert;
    /* 速度 */
    private int speed;
    /* 密度 */
    private int density;
    /* 颜色 */
    private int fillColor;
    /* 间隔时间 */
    private int space;
    /* 描边颜色 */
    private int strokeColor;
    /* 描边宽度 */
    private float strokeWidth;
    /* 文字 */
    private String text;
    /* 文字颜色 */
    private int textColor;
    /* 文字大小 */
    private int textSize;
    /* 文本宽度 */
    private int textWidth;
    /* 填充图片 */
    private BitmapDrawable drawable;
    /* 裁剪后的图片 */
    private Bitmap circleImage;
    /* 波浪数组 */
    private Wave[] waves;
    /* 画笔 */
    private Paint paint;
    /* 宽度 */
    private float width;
    /* 高度 */
    private float height;
    /* 图片填充的范围 */
    private Rect rect;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        waveSize = dp2px(array.getInt(R.styleable.WaveView_wv_wave_size, 30));
        waveColor = array.getColor(R.styleable.WaveView_wv_wave_color, 0xE93878);
        fillRadius = dp2px(array.getInt(R.styleable.WaveView_wv_radius, 0));
        fillColor = array.getColor(R.styleable.WaveView_wv_fill_color, 0xE93878);
        strokeColor = array.getColor(R.styleable.WaveView_wv_stroke_color, 0xFFFFFF);
        strokeWidth = array.getFloat(R.styleable.WaveView_wv_stroke_width, 0.0f);
        text = array.getString(R.styleable.WaveView_wv_text);
        textColor = array.getColor(R.styleable.WaveView_wv_text_color, 0xFFFFFF);
        textSize = dp2px(array.getInt(R.styleable.WaveView_wv_text_size, 12));
        drawable = (BitmapDrawable) array.getDrawable(R.styleable.WaveView_wv_src);
        speed = (int) (1000 * array.getFloat(R.styleable.WaveView_wv_speed, 1));
        density = array.getInt(R.styleable.WaveView_wv_density, 3);
        revert = array.getBoolean(R.styleable.WaveView_wv_revert, false);
        array.recycle();
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.TRANSPARENT);
        space = speed / density;
        if (text != null) {
            textWidth = text.length() * textSize;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        initWaves();
        drawWaveCircle(canvas);
        drawCircleImage(canvas);
        drawFillCircle(canvas);
        drawStrokeCircle(canvas);
        drawColorText(canvas);
        super.onDraw(canvas);
        invalidate();
    }

    /**
     * 绘制填充圆
     *
     * @param canvas 画布
     */
    private void drawFillCircle(Canvas canvas) {
        if (fillRadius != 0 && circleImage == null) {
            paint.setColor(fillColor);
            paint.setAlpha(255);
            canvas.drawCircle(width / 2, height / 2, fillRadius, paint);
        }
    }

    /**
     * 绘制波浪
     *
     * @param canvas 画布
     */
    private void drawWaveCircle(Canvas canvas) {
        paint.setColor(waveColor);
        for (Wave wave : waves) {
            paint.setAlpha(wave.getAlpha());
            canvas.drawCircle(width / 2, height / 2, wave.getRadius(), paint);
        }
    }

    /**
     * 绘制描边圆
     *
     * @param canvas 画布
     */
    private void drawStrokeCircle(Canvas canvas) {
        if (fillRadius != 0 && strokeWidth != 0) {
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            paint.setAlpha(255);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(width / 2, height / 2, fillRadius, paint);
            paint.setStrokeWidth(0);
            paint.setStyle(Paint.Style.FILL);
        }
    }

    /**
     * 绘制颜色文字
     *
     * @param canvas 画布
     */
    private void drawColorText(Canvas canvas) {
        if (text != null) {
            paint.setColor(textColor);
            paint.setAlpha(255);
            canvas.drawText(text, (width / 2) - (textWidth / 2), (height / 2) + (textSize / 3), paint);
        }
    }

    /**
     * 绘制圆形图片
     *
     * @param canvas 画布
     */
    private void drawCircleImage(Canvas canvas) {
        if (circleImage == null && drawable != null) {
            circleImage = clipCircle(drawable.getBitmap());
            drawable.getBitmap().recycle();
            drawable = null;
            rect = new Rect(((int) (width / 2 - fillRadius)),
                    ((int) (height / 2 - fillRadius)),
                    (int) (width - ((int) (width / 2 - fillRadius))),
                    (int) (height - ((int) (height / 2 - fillRadius))));
        }
        if (circleImage != null) {
            paint.setAlpha(255);
            canvas.drawBitmap(circleImage, null, rect, paint);
        }
    }

    /**
     * 创建波浪配置
     *
     * @param delay 波浪与波浪之间的间隔时间
     * @return 返回一个 {@link WaveConfig} 对象
     */
    private WaveConfig createWaveConfig(int delay) {
        WaveConfig config = new WaveConfig();
        config.setWaveSize(waveSize);
        config.setRadius(fillRadius);
        config.setSpeed(speed);
        config.setRevert(revert);
        config.setDelay(delay);
        return config;
    }

    /**
     * 初始化波浪
     */
    private void initWaves() {
        if (waves == null) {
            waves = new Wave[density];
            for (int i = 0; i < waves.length; i++) {
                WaveConfig config = createWaveConfig(i * space);
                waves[i] = new Wave(config);
            }
        }
    }

    /**
     * 裁剪圆形图片
     *
     * @param bitmap 缩放后的正方形
     * @return 裁剪为圆形后的图片
     */
    private Bitmap clipCircle(Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bmp = clipSquare(bitmap);
        Bitmap output = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff424242);
        canvas.drawCircle(bmp.getWidth() / 2, bmp.getWidth() / 2, bmp.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, rect, rect, paint);
        return output;
    }

    /**
     * 将图像裁剪为正方形
     *
     * @param bitmap 原图
     * @return 裁剪后的正方形图片
     */
    private Bitmap clipSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int minWidth = width > height ? height : width;
        int leftTopX = (width - minWidth) / 2;
        int leftTopY = (height - minWidth) / 2;
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, leftTopX, leftTopY, minWidth, minWidth, null, false);
        return scaleBitmap(newBitmap);
    }

    /**
     * 缩放原图
     *
     * @param bitmap 原图位图
     * @return 返回缩放后的图片
     */
    private Bitmap scaleBitmap(Bitmap bitmap) {
        int width = getWidth();
        float scale = (float) width / (float) bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

    /**
     * pd转px
     *
     * @param dp dp值
     * @return 转换后的px值
     */
    private int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
