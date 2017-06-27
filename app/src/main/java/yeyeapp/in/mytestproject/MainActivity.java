package yeyeapp.in.mytestproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import yeyeapp.in.mytestproject.Utils.MyLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyLog.log("log test");
    }
}
