package yeyeapp.in.mytestproject;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;

/**
 * <pre>
 *     author : mac
 *     e-mail : 465680056@qq.com
 *     time   : 2017/06/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    void initLogger() {
        boolean isDebug = true;
        Settings logger = Logger.init("生哥哥(メ｀[]´)/ ")
                .hideThreadInfo()
                .methodCount(4);
        if (isDebug) {
            logger.logLevel(LogLevel.FULL);//todo LogLevel.NONE 发布时设置为LogLevel.NONE
        } else {
            logger.logLevel(LogLevel.NONE);
        }
    }
}
