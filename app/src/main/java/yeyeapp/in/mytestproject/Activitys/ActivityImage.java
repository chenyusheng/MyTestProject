package yeyeapp.in.mytestproject.Activitys;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.Utils.GlideUtils;
import yeyeapp.in.mytestproject.Utils.MyLog;

/**
 * Created by shengbro on 2017/7/10.
 */

public class ActivityImage extends BaseActivity {

    ImageView iv_img;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_image);
    }

    @Override
    public void getData() {
        MyLog.log("开始getData");
        GlideUtils.showImage(activity, iv_img, "http://cdn.mygxt.com/mygxt/201408/25/201408251625077031.jpg");
    }

    @Override
    public void initView() {
        iv_img = (ImageView) contentView.findViewById(R.id.iv_img);
        setTitleText("Activity Image");
        setActionVisiable(false);
        activity = this;
        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMesg("点击图片", false);
                GlideUtils.showImage(activity, iv_img, "http://cdn.mygxt.com/mygxt/201408/25/201408251625077031.jpg");
            }
        });
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyLog.log("back press");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MyLog.log("onKeyDown,keyCode = "+keyCode+" event = "+event.getAction());
        return super.onKeyDown(keyCode, event);

    }
}
