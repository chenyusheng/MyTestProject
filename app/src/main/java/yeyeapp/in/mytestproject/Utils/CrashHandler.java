package yeyeapp.in.mytestproject.Utils;

import android.content.Context;


import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;

/*
 * 崩溃处理
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler handlerInstance = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return handlerInstance;
    }

    public void init(Context ctx) {
        mContext = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        MyLog.log("异常类型\n" + ex.getCause().getClass().getName());

        if (!handleException(ex) && mHandler != null) {
            mHandler.uncaughtException(thread, null);
        } else {
//			Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
            // 上传至云测
            MyLog.log("崩溃处理 CrashHandler = \n" +
                    "   " + ex.getMessage());
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        //处理链接超时
        if (ex instanceof SocketTimeoutException) {
//			if (mContext != null) {
//				Toast.makeText(mContext,"链接超时",Toast.LENGTH_SHORT).show();
//			}
            EventBus.getDefault().post("SocketTimeout");
            return true;
        }
        return ex != null;

    }
}