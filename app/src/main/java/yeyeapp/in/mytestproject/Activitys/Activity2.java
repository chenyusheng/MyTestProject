package yeyeapp.in.mytestproject.Activitys;

import android.os.Bundle;
import android.view.View;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.Utils.MyLog;

/**
 * Created by shengbro on 2017/7/10.
 */

public class Activity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentLayout(R.layout.activity_main);

    }

    @Override
    public void getData() {

    }

    @Override
    public void initView() {
        setTitleText("Activity 2");
        setActionlickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMesg("在activity2 点击 action",false);
            }
        });
        setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyLog.log("in activity2");
                finish();
            }
        });
    }
}
