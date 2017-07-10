package yeyeapp.in.mytestproject;

import android.app.Application;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;
import com.tencent.bugly.Bugly;

import yeyeapp.in.mytestproject.Utils.AppDataUtil;
import yeyeapp.in.mytestproject.Utils.CrashHandler;

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
        Utils.init(this);
        AppDataUtil.getInstance().init( this,true);//初始化项目数据管理工具
        initErrorHandler(this);
        initBugly();
    }

    //初始化工具集
    //一个很全的工具集合库 文档：https://github.com/Blankj/AndroidUtilCode 简书：http://www.jianshu.com/p/72494773aace
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
    /*
    * 错误收集可以用 bugly
    * */
    private void initErrorHandler(MyApplication appInstance) {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(appInstance);
    }

    private void initBugly(){
        Bugly.init(getApplicationContext(), "583a88c62c", !AppDataUtil.getInstance().isReleaseFlag());
    }

}
