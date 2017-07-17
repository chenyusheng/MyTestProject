package yeyeapp.in.mytestproject.Activitys;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.Utils.MyLog;

/**
 * Created by shengbro on 2017/7/10.
 */

public class Activity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentLayout(R.layout.activity2);

    }

    @Override
    public void getData() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        setTitleText("Activity 2");
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setActionlickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMesg("在activity2 点击 action", false);
            }
        });
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyLog.log("in activity2");
                finish();
            }
        });
        Button button = (Button) contentView.findViewById(R.id.btn_text);
        Button btn2 = (Button) contentView.findViewById(R.id.btn2);

        final Notification.Builder builder2 = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("悬浮窗的title")
                .setAutoCancel(true)
                .setContentText("notification content text");
        Intent push = new Intent();
        push.setClass(this,ActivityImage.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,push,PendingIntent.FLAG_CANCEL_CURRENT);
        builder2.setContentIntent(pendingIntent2);
        builder2.setContentText("notification content text 2").setFullScreenIntent(pendingIntent2,true);


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(v, v.getMeasuredWidth() / 2, v.getMeasuredHeight() / 2, v.getMeasuredWidth(), 0);
                final Animator animator2 = ViewAnimationUtils.createCircularReveal(v, v.getMeasuredWidth() / 2, v.getMeasuredHeight() / 2, 0, v.getMeasuredWidth());
                animator.setDuration(1000);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        MyLog.log("动画开始");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        MyLog.log("动画结束");
                        animator2.setDuration(1000);
                        animator2.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        MyLog.log("动画取消");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        MyLog.log("动画重复");
                    }
                });
                animator.start();
                notificationManager.notify(10010,builder2.build());
            }
        });

        final Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("测试的title");
        builder.setContentText("测试的text");
        Intent intent = new Intent(this,ActivityImage.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);//普通的通知
//        builder.setFullScreenIntent(pendingIntent,true);//悬浮式通知
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.empty_view));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager.notify(10086,builder.build());
            }
        });


        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMesg("长按我了");
                return true;
            }
        });
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }


}
