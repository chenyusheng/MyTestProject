package yeyeapp.in.mytestproject;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.annotation.Documented;
import java.util.Collection;
import java.util.Iterator;

import yeyeapp.in.mytestproject.Activitys.Activity2;
import yeyeapp.in.mytestproject.Activitys.ActivityImage;
import yeyeapp.in.mytestproject.Activitys.BaseActivity;
import yeyeapp.in.mytestproject.Utils.ConstantUtil;
import yeyeapp.in.mytestproject.Utils.GlideUtils;
import yeyeapp.in.mytestproject.Utils.MyLog;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ImageView iv;
    Button floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentLayout(R.layout.activity_main);
//        floatingButton = new Button(this);
//        floatingButton.setText("Test");
//        floatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "点击悬浮按钮", Toast.LENGTH_SHORT).show();
//            }
//        });
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
//        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        layoutParams.x = 200;
//        layoutParams.y = 500;
//        WindowManager windowManager = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
//        windowManager.addView(floatingButton, layoutParams);

    }

    void testDisplayMetrics() {
        //测试三种 获取屏幕宽度的区别
        MyLog.log(" getWindow().getDecorView()\n width = " + getWindow().getDecorView().getWidth() + "\n height = " + getWindow().getDecorView().getHeight());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        MyLog.log("getResources().getDisplayMetrics()\n width = " + metrics.widthPixels + "\n height = " + metrics.heightPixels);
        WindowManager windowManager = getWindowManager();
        DisplayMetrics metrics1 = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        MyLog.log("windowManager.getDefaultDisplay().getMetrics\n width = " + metrics1.widthPixels + "\n height = " + metrics1.heightPixels);
    }

    /*
    * 给定一个 n
    * 输出1~n 的字符串
    * */

    void testPriintN(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            stringBuilder.append(i + 1);
        }
        MyLog.log("" + stringBuilder.toString());
    }

    void testArrayMap() {
        ArrayMap<Integer, Integer> arrayMap = new ArrayMap<>();
        arrayMap.put(0, 0);
        arrayMap.put(1, 1);
        arrayMap.put(2, 2);
        arrayMap.put(3, 3);
        arrayMap.put(4, 4);
        arrayMap.put(4, 7);
        arrayMap.put(1, 79);
        arrayMap.put(2, 2);
        Collection<Integer> collection = arrayMap.values();
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> integerIterator = collection.iterator();
        while (integerIterator.hasNext()) {
            sb.append("\n value = " + integerIterator.next());
        }
        MyLog.log(sb.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                Intent i = new Intent(this, ActivityImage.class);
//                i.putExtra(ConstantUtil.FlagEnter,ConstantUtil.TransitionFade);
                i.putExtra(ConstantUtil.FlagExit,ConstantUtil.TransitionFade);
//                startActivity(new Intent(this, ActivityImage.class));
//                startActivity(new Intent(this, ActivityImage.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this,v,"my_pic").toBundle());

                break;
            case R.id.tv_test_metric:
                testDisplayMetrics();
                break;
            case R.id.tv_test3:
                startActivity(new Intent(this, Activity2.class));
//                CrashReport.testJavaCrash();
                break;
            case R.id.tv_test1:
//                testPriintN(9);
                testArrayMap();
                break;
        }
    }

    @Override
    public void getData() {
        GlideUtils.showImage(this, iv, "http://cdn.mygxt.com/mygxt/201408/25/201408251625077031.jpg", GlideUtils.getRequestOptions().circleCrop().centerCrop());
    }

    @Override
    public void initView() {
        setTitleText("Test Project 主页");
        setActionVisiable(false);
        setBackVisiable(false);
        contentView.findViewById(R.id.tv_test_metric).setOnClickListener(this);
        contentView.findViewById(R.id.tv_test1).setOnClickListener(this);
        contentView.findViewById(R.id.tv_test3).setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
    }
}
