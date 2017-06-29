package yeyeapp.in.mytestproject.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.yeyeapp.R;
import in.yeyeapp.listeners.OnItemClickListener;
import in.yeyeapp.utils.CircularAnim;
import in.yeyeapp.utils.Util_Common;

/**
 * Created by yusheng on 2017/3/15.
 * 自定义的 listview
 * 带有下拉刷新
 * 可设置header
 * 可设置 footer
 * 有 loadingview
 * 有 emptyView
 * <p>
 * 可配置多种参数
 */

public class YeYeListView extends RelativeLayout {
    private SwipeRefreshLayout swipeLayout;
    private ListView listLayout;
    private View loadingView, emptyView;
    private TextView emptyText;
    private int duration = 250;


    public YeYeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public YeYeListView(Context context) {
        super(context);
    }

    public YeYeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.yeye_listview, this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.yeye_swipe_layout);
        listLayout = (ListView) findViewById(R.id.yeye_list_layout);
        loadingView = findViewById(R.id.emptyView);
        emptyView = findViewById(R.id.empty_layout);
        emptyView.setBackgroundColor(context.getResources().getColor(R.color.bg_black));
        emptyText = (TextView) emptyView.findViewById(R.id.tv_desc);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YeYeListView);
        emptyText.setText(a.getString(R.styleable.YeYeListView_empty_text));
        emptyText.setTextColor(a.getColor(R.styleable.YeYeListView_empty_textColor, context.getResources().getColor(R.color.text_color_gray)));
        swipeLayout.setColorSchemeColors(a.getColor(R.styleable.YeYeListView_refresh_Color, context.getResources().getColor(R.color.golden)));
        emptyText.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.YeYeListView_empty_textSize, Util_Common.getPxFromDp(context, 14)));
        duration = a.getInt(R.styleable.YeYeListView_yeye_duration, 250);

        a.recycle();
//        View v = getActivity().getLayoutInflater().inflate(getResources().getLayout(R.layout.layout_no_personal_alcohol), null);

    }


    //    设置空状态提示文本
    public void setEmptyText(String s) {
        emptyText.setText(s);
    }

    //是否转为 loading
    public void setLoadinging(boolean b) {
        if (b) {
            if (loadingView.getVisibility() != VISIBLE) {
                CircularAnim.show(loadingView).triggerView(loadingView).duration(duration).go();
            }
        } else {
            if (loadingView.getVisibility() == VISIBLE) {
                CircularAnim.hide(loadingView).triggerView(loadingView).duration(duration).go();
            }
        }
    }

    //设置 swiplayout 的刷新状态
    public void setRefreshing(boolean b) {
        if (swipeLayout == null) {
            return;
        }
        if (swipeLayout.isRefreshing() != b) {
            swipeLayout.setRefreshing(b);
        }
    }

    //指定 listview 滚动到某一位置
    public void smoothScrollTo(int pos) {
        listLayout.smoothScrollToPosition(pos);
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

    //为 listview 添加头部
    public void addHeaderView(View header) {
        listLayout.addHeaderView(header);
    }

    //为 listview 添加尾部
    public void addFooterView(View footer) {
        listLayout.addFooterView(footer);
    }

    //点击监听
    public void setOnItemClickListener(OnItemClickListener listener) {
        listLayout.setOnItemClickListener((AdapterView.OnItemClickListener) listener);
    }

    //添加刷新监听
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeLayout.setOnRefreshListener(listener);
    }

    //设置是否为空
    public void setEmpty(boolean b) {
        if (b) {
            if (emptyView.getVisibility() != VISIBLE) {
                CircularAnim.show(emptyView).triggerView(emptyView).duration(duration).go();
            }
        } else {
            if (emptyView.getVisibility() == VISIBLE) {
                CircularAnim.hide(emptyView).triggerView(emptyView).duration(duration).go();
            }
        }
    }

    //设置适配器
    public void setAdapter(ListAdapter adapter) {
        listLayout.setAdapter(adapter);
        listLayout.getAdapter().registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                //是否显示空状态
                if (listLayout.getAdapter() != null) {
                    setEmpty(listLayout.getAdapter().getCount() == 0);
                }
                setLoadinging(false);
                setRefreshing(false);
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
            }
        });
    }

}
