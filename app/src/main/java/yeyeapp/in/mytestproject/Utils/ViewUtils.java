package yeyeapp.in.mytestproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import yeyeapp.in.mytestproject.Glide.GlideApp;
import yeyeapp.in.mytestproject.R;

/**
 * <pre>
 *     author : mac
 *     e-mail : 465680056@qq.com
 *     time   : 2017/06/29
 *     desc   :主要是 view 的一些相关的操作，如 setText imageView.setSrc ,view.setVisibility等
 *             解耦图片的加载方式不必关心使用的是 glide picasso fresco还是 UIL，方便之后切换框架
 *     version: 1.0
 *
 * </pre>
 */
public class ViewUtils {

    //textView 的 setText
    static public void setText(TextView t, String s) {
        if (t != null) {
            if (!TextUtils.isEmpty(s)) {
                t.setText(s);
            }
        }
    }

    //view 是否可视
    public static void setViewVisibility(View v, boolean isVisibility) {
        if (v != null) {
            if (isVisibility && v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
                return;
            }
            if (!isVisibility && v.getVisibility() == View.VISIBLE) {
                v.setVisibility(View.GONE);
                return;
            }
        }
    }

    //是否显示 textView 下划线
    public static void setTextViewUnderLine(TextView tv, boolean isShowUnderLine) {
        if (tv == null) {
            return;
        }
        if (isShowUnderLine) {//显示下划线
//            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            tv.setPaintFlags(tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        } else {//不显示下划线
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
        tv.getPaint().setAntiAlias(true);
    }

    //自定义样式的弹窗
    public static void showMyDialog(final AlertDialog alert, String title, String desc, String comfirmStr,
                                    View.OnClickListener comfirlistener, View.OnClickListener cancleListener) {

        View view = View.inflate(alert.getContext(), R.layout.layout_dialog_alert, null);
        TextView titleTv = (TextView) view.findViewById(R.id.title);
        if (TextUtils.isEmpty(title)) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setText(title);
        }
        TextView title_desc = (TextView) view.findViewById(R.id.tv_content);
        title_desc.setText(desc);
        TextView btn_confirm = (TextView) view.findViewById(R.id.btn_ok);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        if (!TextUtils.isEmpty(comfirmStr)) {
            btn_confirm.setText(comfirmStr);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };
        if (comfirlistener == null) {
            comfirlistener = listener;
        }
        if (cancleListener == null) {
            cancleListener = listener;
        }
        alert.setCanceledOnTouchOutside(false);
        btn_confirm.setOnClickListener(comfirlistener);
        btn_cancel.setOnClickListener(cancleListener);
        alert.setView(view);
        alert.show();
    }

    //show Toast design风格的snabar
    public static void showToastWithButton(View rootView, String content, View.OnClickListener listener, String buttonName) {
        if (rootView == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_LONG).setAction(buttonName, listener);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(rootView.getContext().getResources().getColor(R.color.white));//修改文本颜色
        ((TextView) view.findViewById(R.id.snackbar_action)).setTextColor(rootView.getContext().getResources().getColor(R.color.white));//修改按钮文本颜色
        snackbar.show();
    }

    public static void showToast(View rootView, String content) {
        if (rootView == null) {
            return;
        }
        Snackbar snackbar = Snackbar.make(rootView, content, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(rootView.getContext().getResources().getColor(R.color.white));//修改文本颜色
        ((TextView) view.findViewById(R.id.snackbar_action)).setTextColor(rootView.getContext().getResources().getColor(R.color.white));//修改按钮文本颜色
        snackbar.show();
    }

}
