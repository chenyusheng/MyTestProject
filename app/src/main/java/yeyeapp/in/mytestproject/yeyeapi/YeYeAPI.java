package yeyeapp.in.mytestproject.yeyeapi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import yeyeapp.in.mytestproject.model.RetrofitData.RetrofitResult;
import yeyeapp.in.mytestproject.model.RetrofitRequestBody.BookingRequest;

/**
 * Created by mac on 2017/5/9.
 * retrofit 文档 http://square.github.io/retrofit/
 * 动态参数直接用@query
 */

public interface YeYeAPI {

    //获取一个用户的信息
    @GET("userinfo")
    Observable<RetrofitResult> getUserData2(@Query("mdid") String mdid);


    //todo post参数很多的话也可以使用@body User u 这样子来请求
    @FormUrlEncoded
    @POST("staff/withdrawal")
    Observable<RetrofitResult> toStaffCashout(@Field("mdid") String mdid, @Field("price") String price);

    @POST("svip/booking")
    Observable<RetrofitResult> toSVIPBooking(@Body BookingRequest BookingRequest);

 }
