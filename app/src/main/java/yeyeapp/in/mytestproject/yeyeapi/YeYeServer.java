package yeyeapp.in.mytestproject.yeyeapi;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mac on 2017/5/9.
 */

public class YeYeServer {

    private static volatile YeYeServer instance = null;
    YeYeAPI yeYeAPI;
    Observer tObserver;

    private YeYeServer() {
        yeYeAPI = RetrofitClient.getInstance().mRetrofit.create(YeYeAPI.class);
    }

    //    单例模式 双重检验
    public static YeYeServer getInstance() {
        if (instance == null) {
            synchronized (YeYeServer.class) {
                if (instance == null) {
                    instance = new YeYeServer();
                }
            }
        }
        return instance;
    }

    void getUserData(String mdid ,Observer observer){
        yeYeAPI.getUserData2(mdid).retry(3).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

}
