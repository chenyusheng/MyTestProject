package yeyeapp.in.mytestproject.yeyeapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yeyeapp.in.mytestproject.Utils.AppDataUtil;
import yeyeapp.in.mytestproject.Utils.MyLog;

public class RetrofitClient {

    public static volatile RetrofitClient mInstance;
    public Retrofit mRetrofit;
    OkHttpClient okio;
    Gson gson;
    Cache cache;
    public static Context context2;


    public static final String BASE_URL = "https://api.yeyeapp.in/v1/";

    private RetrofitClient() {
//        既然能用OkHttp的拦截机制，那么我们就可以在RequestBody 里面添加基本参数 HttpLoggingInterceptor
        gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        //创建缓存路径
        File cacheFile = new File(context2.getCacheDir(), "HttpCache");
        cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        okio = new OkHttpClient();
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(45, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(45, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(45, TimeUnit.SECONDS);
        okHttpClientBuilder.retryOnConnectionFailure(true);
        okHttpClientBuilder.cache(cache);
        okHttpClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
//                if (networkMonitor.isConnected()) {
//                    return chain.proceed(chain.request());
//                } else {
//                    throw new NoNetworkException();
//                }
                Request request = chain.request();
                String s = "retrofit request:";
                if (request != null) {
                    s += "   \n url = " + request.url().toString();
                    s += "   \n method = " + request.method();
                    s += "   \n header = " + request.headers().toString();
                }
                MyLog.log(s);
                return chain.proceed(chain.request());
            }
        });
//        okio.connectTimeoutMillis();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClientBuilder.build())
                .baseUrl(BASE_URL)
                .validateEagerly(!AppDataUtil.getInstance().isReleaseFlag())
                .build();
    }

    public static void init(Context context){
        context2 = context;
        getInstance();
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null)
                    mInstance = new RetrofitClient();
            }
        }
        return mInstance;
    }
}