package yeyeapp.in.mytestproject.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.yeyeapp.R;
import in.yeyeapp.ui.MainActivity_;
import in.yeyeapp.utils.Util_Common;
import in.yeyeapp.utils.Util_Keyboard;

/**
 * Created by yusheng on 2017/3/21.
 * 自定义的ActionBar组件
 * 带返回动作
 * 带标题
 * 带点搜索框
 * 带 右边设置按钮
 * 右边设置按钮可以设置图标
 */

public class YeYeActionBar extends RelativeLayout {

    private ImageView bg_color, btn_back;
    private TextView btn_title, btn_action;
    private EditText et_search;
    private Context context;
    private View rootView;
    private View divider_dark_line;

    private String yeyeActionbar_titleText, yeyeActionbar_actionText, yeyeActionbar_searchHint;
    private int yeyeActionbar_titleTextColor, yeyeActionbar_actionTextColor, yeyeActionbar_searchTextColor, yeyeActionbar_backgroundColor;
    private int yeyeActionbar_titleTextSize, yeyeActionbar_actionTextSize, yeyeActionbar_searchTextSize;
    private Drawable yeyeActionbar_actionDrawable, yeyeActionbar_searchDrawable;
    private boolean yeyeActionbar_actionDrawableIsLeft, yeyeActionbar_IsSearch, yeyeActionbar_IsShowAction, yeyeActionBar_IsShowStatuBar, yeyeActionBar_isShowDivider;

    private OnClickListener backListener, actionListener;

    public YeYeActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public YeYeActionBar(Context context) {
        super(context);
        this.context = context;
    }

    public YeYeActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 加载布局
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_actionbar, this);

        divider_dark_line = findViewById(R.id.divider_dark_line);
        btn_title = (TextView) findViewById(R.id.btn_title);
        btn_action = (TextView) findViewById(R.id.btn_action);
        bg_color = (ImageView) findViewById(R.id.bg_color);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        et_search = (EditText) findViewById(R.id.et_search);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YeYeActionBar);
        yeyeActionbar_titleText = a.getString(R.styleable.YeYeActionBar_yeyeActionbar_titleText);
        yeyeActionbar_actionText = a.getString(R.styleable.YeYeActionBar_yeyeActionbar_actionText);
        yeyeActionbar_searchHint = a.getString(R.styleable.YeYeActionBar_yeyeActionbar_searchHint);

        yeyeActionbar_titleTextColor = a.getColor(R.styleable.YeYeActionBar_yeyeActionbar_titleTextColor, context.getResources().getColor(R.color.white));
        yeyeActionbar_actionTextColor = a.getColor(R.styleable.YeYeActionBar_yeyeActionbar_actionTextColor, context.getResources().getColor(R.color.white));
        yeyeActionbar_searchTextColor = a.getColor(R.styleable.YeYeActionBar_yeyeActionbar_searchTextColor, context.getResources().getColor(R.color.text_color_gray));
        yeyeActionbar_backgroundColor = a.getColor(R.styleable.YeYeActionBar_yeyeActionbar_backgroundColor, context.getResources().getColor(R.color.bg_black));

        yeyeActionbar_titleTextSize = a.getDimensionPixelSize(R.styleable.YeYeActionBar_yeyeActionbar_titleTextSize, Util_Common.getPxFromDp(context, 16));
        yeyeActionbar_actionTextSize = a.getDimensionPixelSize(R.styleable.YeYeActionBar_yeyeActionbar_actionTextSize, Util_Common.getPxFromDp(context, 14));
        yeyeActionbar_searchTextSize = a.getDimensionPixelSize(R.styleable.YeYeActionBar_yeyeActionbar_searchTextSize, Util_Common.getPxFromDp(context, 12));

        yeyeActionbar_actionDrawable = a.getDrawable(R.styleable.YeYeActionBar_yeyeActionbar_actionDrawable);
        yeyeActionbar_searchDrawable = a.getDrawable(R.styleable.YeYeActionBar_yeyeActionbar_searchDrawable);

        yeyeActionbar_actionDrawableIsLeft = a.getBoolean(R.styleable.YeYeActionBar_yeyeActionbar_actionDrawableIsLeft, true);
        yeyeActionbar_IsSearch = a.getBoolean(R.styleable.YeYeActionBar_yeyeActionbar_IsSearch, false);
        yeyeActionbar_IsShowAction = a.getBoolean(R.styleable.YeYeActionBar_yeyeActionbar_IsShowAction, false);
        yeyeActionBar_IsShowStatuBar = a.getBoolean(R.styleable.YeYeActionBar_yeyeActionBar_IsShowStatuBar, false);
        yeyeActionBar_isShowDivider = a.getBoolean(R.styleable.YeYeActionBar_yeyeActionbar_isShowDivider, false);

        a.recycle();
        //根据属性配置 View
        setStatuHeight(yeyeActionBar_IsShowStatuBar);
        setBackgroundColor(yeyeActionbar_backgroundColor);
        setIsSearchView(yeyeActionbar_IsSearch);
        setIsShowAction(yeyeActionbar_IsShowAction);
        setTitleText(yeyeActionbar_titleText, yeyeActionbar_titleTextColor, yeyeActionbar_titleTextSize);
        setAction(yeyeActionbar_actionText, yeyeActionbar_actionTextSize, yeyeActionbar_actionTextColor, yeyeActionbar_actionDrawable, yeyeActionbar_actionDrawableIsLeft);
        setSearch(yeyeActionbar_searchHint, yeyeActionbar_searchTextSize, yeyeActionbar_searchTextColor, yeyeActionbar_searchDrawable);
        setDivider(yeyeActionBar_isShowDivider);

        setBackListener(null);
    }

    public void setDivider(boolean b) {
        if (b) {
            if (divider_dark_line.getVisibility() != VISIBLE) {
                divider_dark_line.setVisibility(VISIBLE);
            }
        } else {
            if (divider_dark_line.getVisibility() != GONE) {
                divider_dark_line.setVisibility(GONE);
            }
        }
    }

    //设置 order_title
    public void setTitleText(String title) {
        btn_title.setText(title);
    }

    public void setTitleText(String title, int color) {
        btn_title.setTextColor(color);
        setTitleText(title);
    }

    public void setTitleText(String title, int color, int textSize) {
        btn_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setTitleText(title, color);
//        Logger.e("order_title = "+order_title+" color = "+color+" textSize = "+textSize);
    }

    //设置 action
    public void setIsShowAction(boolean i) {
        if (i) {
            btn_action.setVisibility(VISIBLE);
        } else {
            btn_action.setVisibility(GONE);
        }
    }

    public void setActionText(String actionText) {
        if (TextUtils.isEmpty(actionText)) {
            return;
        }
        btn_action.setText(actionText);
    }

    public void setActionTextSize(int textSize) {
        if (textSize <= 0) {
            return;
        }
        btn_action.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setActionTextColor(int color) {
        btn_action.setTextColor(color);
    }

    public void setActionDrawable(Drawable drawable, boolean isLeft) {
        if (drawable == null) {
            return;
        }
        if (isLeft) {
            btn_action.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            btn_action.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }

    public void setAction(String actionText, int textSize, int color, Drawable drawable, boolean isLeft) {
//        Logger.e("actionText = "+actionText+" color = "+color+" textSize = "+textSize+" isLeft = "+isLeft);
        setActionText(actionText);
        setActionTextSize(textSize);
        setActionTextColor(color);
        setActionDrawable(drawable, isLeft);
    }

    //设置 search View
    public void setSearchText(String text) {
        et_search.setHint(text);
    }

    public void setSearchTextSize(int size) {
        if (size <= 0) {
            return;
        }
        et_search.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public void setSearchTextColor(int color) {
        et_search.setTextColor(color);
    }

    public void setSearchDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        et_search.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setSearch(String searchHint, int textSize, int color, Drawable drawable) {
//        Logger.e("searchText = "+searchHint+" color = "+color+" textSize = "+textSize);
        setSearchText(searchHint);
        setSearchTextSize(textSize);
        setSearchTextColor(color);
        setSearchDrawable(drawable);
    }

    public void setIsSearchView(boolean isSearchView) {
        if (isSearchView) {
            et_search.setVisibility(VISIBLE);
            btn_title.setVisibility(GONE);
        } else {
            et_search.setVisibility(GONE);
            btn_title.setVisibility(VISIBLE);
        }
    }

    public void hideKeyBoard() {
        Util_Keyboard.hideKeybord(et_search);
    }

    //设置 background
    public void setBackgroundColor(int color) {
        bg_color.setBackgroundColor(color);
    }

    //设置背景透明度 alpha value (0 < alpha < 1)
    public void setBackgroundAlpha(float alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 1) {
            alpha = 1;
        }
        bg_color.setAlpha(alpha);
    }

    //是否需要状态栏
    public void setStatuHeight(boolean i) {
        if (i) {
            rootView.setPadding(0, Util_Common.getStatusBarHeight(((Activity) context)), 0, 0);
        } else {
            rootView.setPadding(0, 0, 0, 0);
        }
    }

    //设置点击事件
    public void setBackListener(OnClickListener listener) {
        if (listener == null) {
            //默认就是返回
            listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Activity) context) instanceof MainActivity_) {
                        ((MainActivity_) context).popFragment();
                    }
                }
            };
        }
        btn_back.setOnClickListener(listener);
    }

    //动作按钮没有默认事件
    public void setActionListener(OnClickListener listener) {
        btn_action.setOnClickListener(listener);
    }

    //order_title 可以点击
    public void setTitleClickListener(OnClickListener l) {
        btn_title.setOnClickListener(l);
    }

    //获取搜索框输入内容
    public String getSearchContent() {
        if (et_search.getText() != null) {
            return et_search.getText().toString();
        }
        return "";
    }


}
