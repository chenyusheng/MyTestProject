package yeyeapp.in.mytestproject.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import yeyeapp.in.mytestproject.R;


/**
 * by yusheng
 * 自定义评分view 组件
 * 可自定义开始角度
 * 进度条宽度
 * 颜色
 * 动画执行时间
 */
//import com.nineoldandroids.animation.ValueAnimator;

public class MarkingView extends View {


    //底色的圆
    private float bottomWidth = 200;
    private Paint bottomPaint;
    private int bottomColor = Color.GRAY;//底圆的颜色
    //上层的圆
    private float topWidth;
    private Paint topPaint;
    private int topColor = Color.DKGRAY;//扇形的颜色

    private int ringWidth = 20;//圆环的宽度

    private int angle = 270;//上层圆环的目标着色角度
    private int currentAngle = 0;//上层圆环的着色角度
    private int startAngle = 180;//上层圆环的着色开始角度

    private int centerColor = Color.WHITE;//圆环中间的颜色
    private Paint centerPaint;

    private boolean isFirst = true;
    private int duation = 3000;//动画的时间长度，默认3秒

    public MarkingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        topPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //从attr获取参数
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MarkingView);
        setBottomColor(a.getColor(R.styleable.MarkingView_bottomColor, bottomColor));
        setTopColor(a.getColor(R.styleable.MarkingView_topColor, topColor));
        setCenterColor(a.getColor(R.styleable.MarkingView_centerColor, centerColor));

        setEndAngle(a.getInteger(R.styleable.MarkingView_endAngle, angle));
        setStartAngle(a.getInteger(R.styleable.MarkingView_startAngle, startAngle));
        setDuation(a.getInteger(R.styleable.MarkingView_duation, duation));
        setRingWidth(a.getDimensionPixelOffset(R.styleable.MarkingView_ringWidth, ringWidth));

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mPaint.setColor(Color.WHITE);
////        mPaint.setAlpha(0);
//        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        if (isFirst) {
            isFirst = false;
            topWidth = getWidth();
            bottomWidth = getWidth();

            drawBottom(canvas);
            drawTop(canvas);
            drawCenter(canvas);
            startAnimation();
        } else {
            drawBottom(canvas);
            drawTop(canvas);
            drawCenter(canvas);
        }


    }

    public void refresh() {
        startAnimation();
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setDuation(int duation) {
        this.duation = duation;
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
    }

    public void setTopColor(int topColor) {
        this.topColor = topColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    public void setEndAngle(int angle) {
        this.angle = angle;
    }

    public void setBottomColor(int bottomColor) {
        this.bottomColor = bottomColor;
    }

    protected void drawBottom(Canvas canvas) {
        bottomPaint.setColor(bottomColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, bottomWidth / 2, bottomPaint);
    }

    protected void drawTop(Canvas canvas) {
        topPaint.setColor(topColor);
        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        canvas.drawArc(rectF, startAngle, currentAngle, true, topPaint);
    }

    protected void drawCenter(Canvas canvas) {
        centerPaint.setColor(centerColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, (topWidth - ringWidth * 2) / 2, centerPaint);
    }

    private void startAnimation() {

        ValueAnimator anim = ValueAnimator.ofInt(0, angle);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(duation);
        anim.start();
    }

}  