package yeyeapp.in.mytestproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import yeyeapp.in.mytestproject.Utils.GlideUtils;
import yeyeapp.in.mytestproject.Utils.MyLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_test_metric).setOnClickListener(this);
        findViewById(R.id.tv_test1).setOnClickListener(this);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                MyLog.log("延时三秒之后 测试 ");
//                testDisplayMetrics();
//            }
//        }, 3000);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                GlideUtils.showImage(this,iv,"http://cdn.mygxt.com/mygxt/201408/25/201408251625077031.jpg",GlideUtils.getRequestOptions().circleCrop().centerCrop());
                break;
            case R.id.tv_test_metric:
                testDisplayMetrics();
                break;
            case R.id.tv_test1:
                testPriintN(9);
                break;
        }
    }
}
