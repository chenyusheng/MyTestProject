package yeyeapp.in.mytestproject.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.Utils.CircularAnim;
import yeyeapp.in.mytestproject.Utils.CommonUtils;
import yeyeapp.in.mytestproject.listener.OnSuccessListener;

/**
 * Created by yusheng on 2017/3/15.
 * 自定义的金色按钮组件
 * 带圆角
 * 带动画
 * 带点击效果
 * 带 loading 状态
 */

public class YeYeButtomGoldenView extends RelativeLayout {

    private TextView tv_btn;
    private CardView cardView;
    private ImageView loadingImageView;
    Animation animation = null;
    int forbidTextColor = R.color.send_forbid;
    private int duration = 250;
    private boolean loading = false;


    public YeYeButtomGoldenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public YeYeButtomGoldenView(Context context) {
        super(context);
    }

    public YeYeButtomGoldenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.btn_goleden_view, this);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        cardView = (CardView) findViewById(R.id.btn_yeye_buttom);
        loadingImageView = (ImageView) findViewById(R.id.loadingImageView);
        animation = AnimationUtils.loadAnimation(context,
                R.anim.loading);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YeYeButtomGoldenView);
        tv_btn.setText(a.getString(R.styleable.YeYeButtomGoldenView_btn_text));
        tv_btn.setTextColor(a.getColor(R.styleable.YeYeButtomGoldenView_btn_textColor, context.getResources().getColor(R.color.text_golden)));
        tv_btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.YeYeButtomGoldenView_btn_textSize, CommonUtils.sp2px(context, 16)));
        duration = a.getInt(R.styleable.YeYeButtomGoldenView_btn_duration, 250);
        cardView.setRadius(CommonUtils.dp2px(context, a.getInt(R.styleable.YeYeButtomGoldenView_btn_radius, 5)));
        cardView.setUseCompatPadding(a.getBoolean(R.styleable.YeYeButtomGoldenView_btn_compat_padding, false));
        a.recycle();
    }

    public void setBtnBackgroundColor(int color) {
        cardView.setCardBackgroundColor(color);
    }

    public void setBackgroundRadius(int i) {
        cardView.setRadius(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setElevation(float size) {
        cardView.setElevation(size);
    }

    public void setBtnEnable(boolean b) {
        if (b) {
            tv_btn.setTextColor(getResources().getColor(R.color.seletor_text_color_yeye_gold_btn));
        } else {
            tv_btn.setTextColor(getResources().getColor(forbidTextColor));
        }
        cardView.setEnabled(b);

        this.setEnabled(b);
    }

    public void setForbidTextColor(int i) {
        forbidTextColor = i;
    }

    public TextView getTv_btn() {
        return tv_btn;
    }

    //设置文本
    public void setText(String str) {
        tv_btn.setText(str);
    }

    //设置文本大小
    public void setTextSize(float size) {
        tv_btn.setTextSize(size);
    }

    //设置文本颜色
    public void setTextColor(int color) {
        tv_btn.setTextColor(color);
    }

    //是否转为 loading
    public void showLoading(boolean b) {
        loading = b;
        if (b) {
            CircularAnim.show(loadingImageView).triggerView(loadingImageView).duration(duration).go();
            loadingImageView.setAnimation(animation);
            loadingImageView.startAnimation(animation);
        } else {
            CircularAnim.hide(loadingImageView).triggerView(loadingImageView).duration(duration).go();
            loadingImageView.clearAnimation();
        }
    }

    public void showLoading(boolean b, final OnSuccessListener listener) {
        if (b) {
            loadingImageView.setAnimation(animation);
            loadingImageView.startAnimation(animation);
            CircularAnim.show(loadingImageView).triggerView(loadingImageView).duration(duration).go(new CircularAnim.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    listener.onSuccess();
                }
            });

        } else {
            loadingImageView.clearAnimation();
            CircularAnim.hide(loadingImageView).triggerView(loadingImageView).duration(duration).go(new CircularAnim.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd() {
                    listener.onSuccess();
                }
            });
        }
    }

    public boolean isLoading() {
        return loading;
    }

    // 显示
    public void showView() {
        CircularAnim.show(this).triggerView(this).duration(duration).go();
    }

    //带 listener 的显示
    public void showView(CircularAnim.OnAnimationEndListener listener) {
        CircularAnim.show(this).triggerView(this).duration(duration).go(listener);
    }

    //隐藏
    public void hideView() {
        CircularAnim.hide(this).triggerView(this).duration(duration).go();
    }

    //带 listener 的隐藏
    public void hideView(CircularAnim.OnAnimationEndListener listener) {
        CircularAnim.hide(this).triggerView(this).duration(duration).go(listener);
    }

    //点击效果 收缩一下
    public void trigger() {
        hideView(new CircularAnim.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                showView();
            }
        });
    }

    //点击监听 实际上被点击的是 tv_btn
    public void setOnBtnClickListener(OnClickListener listener) {
        cardView.setOnClickListener(listener);
    }

}
