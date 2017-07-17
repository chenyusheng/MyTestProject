package yeyeapp.in.mytestproject.Activitys;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.InflateException;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.Utils.ConstantUtil;
import yeyeapp.in.mytestproject.Utils.MyLog;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView btnBack;
    private ImageView btnAction;
    private LinearLayout contentLayout;
    private RelativeLayout actionBar;
    public View contentView;

    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setTransition();
        setContentView(R.layout.base_activity);

        actionBar = (RelativeLayout) findViewById(R.id.action_bar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnAction = (ImageView) findViewById(R.id.btn_action);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        btnBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        btnAction.setOnClickListener(this);
//        setTransition();
    }

    /**
     * 在intent的extras中设置当前activity的切换效果
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void setTransition() {
        MyLog.log("开始设置 动画");
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        String flagEnter = bundle.getString(ConstantUtil.FlagEnter);
        String flagExit = bundle.getString(ConstantUtil.FlagExit);
        //设置进入动画
        if (!TextUtils.isEmpty(flagEnter)) {
            switch (flagEnter) {
                case ConstantUtil.TransitionExplore:
                    getWindow().setEnterTransition(new Explode());
                    break;
                case ConstantUtil.TransitionFade:
                    getWindow().setEnterTransition(new Fade());
                    break;
                case ConstantUtil.TransitionSlide:
                    getWindow().setEnterTransition(new Slide());
                    break;
                default:
            }
        }
        //设置退出动画
        if (!TextUtils.isEmpty(flagExit)) {
            switch (flagExit) {
                case ConstantUtil.TransitionExplore:
                    getWindow().setExitTransition(new Explode());
                    break;
                case ConstantUtil.TransitionFade:
                    getWindow().setExitTransition(new Fade());
                    break;
                case ConstantUtil.TransitionSlide:
                    getWindow().setExitTransition(new Slide());
                    break;
                default:
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            //懒加载
            isFirst = false;
            getData();
        }

    }

    /**
     * 设置主界面的内容区域
     *
     * @param layout_id 内容布局的id
     */
    public void setContentLayout(int layout_id) {
        try {
            contentView = View.inflate(this, layout_id, null);
            contentLayout.removeAllViews();
            contentLayout.addView(contentView);
            if (contentView != null) {
                initView();
            }
        } catch (InflateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否显示返回键 默认显示
     *
     * @param b
     */
    public void setBackVisiable(boolean b) {
        if (b && btnBack != null && btnBack.getVisibility() != View.VISIBLE) {
            btnBack.setVisibility(View.VISIBLE);
            return;
        }
        if (!b && btnBack != null && btnBack.getVisibility() == View.VISIBLE) {
            btnBack.setVisibility(View.GONE);
            return;
        }
    }

    /**
     * 是否显示操作键
     *
     * @param b
     */
    public void setActionVisiable(boolean b) {
        if (b && btnAction != null && btnAction.getVisibility() != View.VISIBLE) {
            btnAction.setVisibility(View.VISIBLE);
            return;
        }
        if (!b && btnAction != null && btnAction.getVisibility() == View.VISIBLE) {
            btnAction.setVisibility(View.GONE);
            return;
        }
    }

    /**
     * 获取操作键，主要用于修改图片及对应的功能
     *
     * @return
     */
    public ImageView getActionBtn() {
        return btnAction;
    }

    /**
     * 设置title的文案
     *
     * @param title
     */
    public void setTitleText(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    /**
     * 设置actionBAr是否可见
     *
     * @param b
     */
    public void setActionBarVisiable(boolean b) {
        if (b && actionBar != null && actionBar.getVisibility() != View.VISIBLE) {
            actionBar.setVisibility(View.VISIBLE);
            return;
        }
        if (!b && actionBar != null && actionBar.getVisibility() == View.VISIBLE) {
            actionBar.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title:
                //todo 例如滚回视图顶部
                break;
            case R.id.btn_back:
                MyLog.log("in base");
                onBackPressed();
                break;
            case R.id.btn_action:
                Toast.makeText(this, "click action!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param l 新的监听器
     */
    public void setBackClickListener(View.OnClickListener l) {
        btnBack.setOnClickListener(l);
    }

    public void setTitleClickListener(View.OnClickListener l) {
        tvTitle.setOnClickListener(l);
    }

    public void setActionlickListener(View.OnClickListener l) {
        btnAction.setOnClickListener(l);
    }

    /**
     * 弹出提示
     *
     * @param s
     * @param isLong
     */
    public void showMesg(String s, boolean isLong) {
        if (isLong) {
//            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            ToastUtils.showLongSafe(s);
        } else {
//            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            ToastUtils.showShortSafe(s);
        }
    }

    public void showMesg(String s) {
        showMesg(s, false);
    }

    /**
     * 开始拉去数据
     */
    public abstract void getData();

    /**
     * 初始化view
     */
    public abstract void initView();
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyLog.log("back press");
        System.gc();
    }
}
