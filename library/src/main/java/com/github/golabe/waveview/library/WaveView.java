package com.github.golabe.waveview.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaveView extends View {

    private static final float DEFAULT_WAVE_HEIGHT = 10F;
    private static final int DEFAULT_WAVE_COUNT = 4;
    private static final int DEFAULT_HEIGHT = 100;
    private static final int DEFAULT_DURATION = 1000;
    private static final float DEFAULT_SELF_HEIGHT = 50;
    private float waveHeight;
    private int waveColor;
    private int waveCount;
    private Paint paint;
    private Path path;
    private int width;
    private int height;
    private int waveLength;
    private int haftWaveLength;
    private int duration;
    private float selfHeight;
    private ValueAnimator animator;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrs(attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(waveColor);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    private void attrs(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WaveView);
            waveHeight = dp2px(a.getDimension(R.styleable.WaveView_wave_height, DEFAULT_WAVE_HEIGHT));
            waveColor = a.getColor(R.styleable.WaveView_wave_color, Color.parseColor("#40b450"));
            waveCount = a.getInt(R.styleable.WaveView_wave_count, DEFAULT_WAVE_COUNT);
            duration = a.getInt(R.styleable.WaveView_duration, DEFAULT_DURATION);
            selfHeight = dp2px(a.getDimension(R.styleable.WaveView_height, DEFAULT_SELF_HEIGHT));
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hModel = MeasureSpec.getMode(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        if (hModel == MeasureSpec.EXACTLY) {
            setMeasuredDimension(w, h);
        } else {
            setMeasuredDimension(w, dp2px(DEFAULT_HEIGHT));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        waveLength = w / waveCount;
        selfHeight = h - selfHeight;

        haftWaveLength = waveLength / 2;

    }

    private int dx;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(-waveLength + dx, selfHeight);
        for (int i = -waveLength; i < width + waveLength; i += waveLength) {
            path.rQuadTo(haftWaveLength / 2, -waveHeight, haftWaveLength, 0);
            path.rQuadTo(haftWaveLength / 2, waveHeight, haftWaveLength, 0);
        }
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();
        canvas.drawPath(path, paint);

    }

    private int dp2px(float dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5F);
    }


    public void startAnimation() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, getWidth() / waveCount);
            animator.setDuration(duration);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    dx = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animator.start();
        }
    }

    public void cancelAnimation() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    public float getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(float waveHeight) {
        this.waveHeight = waveHeight;
        invalidate();
    }

    public int getWaveColor() {
        return waveColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
        invalidate();
    }

    public int getWaveCount() {
        return waveCount;
    }

    public void setWaveCount(int waveCount) {
        this.waveCount = waveCount;
        invalidate();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        invalidate();
    }
}
