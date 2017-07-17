package yeyeapp.in.mytestproject.Utils;

import android.content.Context;

import com.blankj.utilcode.util.CacheUtils;

/**
 * <pre>
 *     author : mac
 *     e-mail : 465680056@qq.com
 *     time   : 2017/06/29
 *     desc   :应用全局数据管理
 *     version: 1.0
 * </pre>
 */
public class AppDataUtil {

    private volatile static AppDataUtil instance;
    private boolean releaseFlag = false;//当前是否开发版
    private Context context;
    CacheUtils cache;//缓存工具

    public static AppDataUtil getInstance(){
        if (instance == null) {
            synchronized (AppDataUtil.class){
                if (instance == null) {
                    instance = new AppDataUtil();
                }
            }
        }
        return instance;
    }
    public  void init(Context c,boolean isRelease){
        cache = CacheUtils.getInstance();
        this.releaseFlag = isRelease;
        this.context = c;
    }

    public boolean isReleaseFlag() {
        return releaseFlag;
    }

}
