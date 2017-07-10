package yeyeapp.in.mytestproject.Utils;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import yeyeapp.in.mytestproject.Glide.GlideApp;
import yeyeapp.in.mytestproject.R;

/**
 * <pre>
 *     author : mac
 *     e-mail : 465680056@qq.com
 *     time   : 2017/06/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GlideUtils {


    //默认的 requesOption
    public static RequestOptions getRequestOptions() {
        //默认的 requestOption
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.format(DecodeFormat.PREFER_ARGB_8888);
        options.placeholder(R.color.imageHolder);//加载中图片占位图
        options.error(R.color.imageError);//加载错误的图片
        options.priority(Priority.HIGH);//设置请求优先级
        options.diskCacheStrategy(DiskCacheStrategy.ALL);

        //options.diskCacheStrategy(DiskCacheStrategy.RESOURCE)//仅缓存原图片
        //options.diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
        //options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)缓存缩略过的
        //options.onlyRetrieveFromCache(true)//仅从缓存加载
        //options.skipMemoryCache(true)//禁用缓存,包括内存和磁盘
        //options.diskCacheStrategy(DiskCacheStrategy.NONE)仅跳过磁盘缓存
        //options.override(200,200)加载固定大小的图片
        //options.dontTransform()不做渐入渐出的转换
        //options.transition(new DrawableTransitionOptions().dontTransition())//同上
        //options.circleCrop()设置成圆形头像<这个是V4.0新增的>
        //options.transform(new RoundedCorners(10))设置成圆角头像<这个是V4.0新增的>

        return options;
    }

    /*
    * 负责图片加载 用户只管调用
    * 不关心背后是怎么实现
    *  这样切换图片加载库成本比较低
    * */
    public static void showImage(Context context, ImageView imageView, String url) {
        showImage(context, imageView, url, getRequestOptions());
    }

    /*
    * 加载带有指定 requestOptions 的图片
    * */
    public static void showImage(Context context, ImageView imageView, String url, RequestOptions requestOptions) {
        if (imageView == null) {
            return;
        }
        GlideApp.with(context)
                .load(url)
                .listener(null)
                .centerCrop()
                .thumbnail(1f)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(250))//渐显效果
                .into(imageView);
    }


    public static void showImage(Fragment fragment, ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        //绑定 fragment 的生命周期
        GlideApp.with(fragment)
                .load(url)
                .apply(getRequestOptions())
                .into(imageView);
    }

    public static void showImage(Activity activity, ImageView imageView, String url) {
        if (imageView == null) {
            MyLog.log("imageView == null");
            return;
        }
        MyLog.log("url = "+url);
        //绑定 Activity 的生命周期
        GlideApp.with(activity)
                .load(url)
                .apply(getRequestOptions())
                .into(imageView);
    }

    public static void showImage(Fragment fragment, ImageView imageView, String url, boolean isWebP) {

        if (isWebP) {
            //七牛的图片链接提供 webp 格式，内存大大的降低了
            url += "?imageMogr2/format/webp";
        }
        showImage(fragment, imageView, url);
    }

    public static void clearImage(Fragment fragment, ImageView imageView) {
        //手动调用 ，实际上 Glide 会根据 Fragment 的生命周期自动停止
        if (fragment != null && imageView != null) {
            Glide.with(fragment).clear(imageView);
        }
    }

    /**
     * 加载圆角图片
     *
     * @param context 如果是activity glide会与其生命周期关联,在onStop()中取消加载图片,如果
     *                想要始终加载图片则需要传入Application实例
     * @param url
     * @param target
     */
    public static void loadRoundedCornersImg(Context context, String url, ImageView target, int corner) {

        GlideApp.with(context)
                .load(url)
                .transform(new RoundedCorners(corner))
                .transition(new DrawableTransitionOptions().crossFade(200))//渐显效果
                .into(target);
    }

    /**
     * 加载圆形头像
     *
     * @param context 如果是activity glide会与其生命周期关联,在onStop()中取消加载图片,如果
     *                想要始终加载图片则需要传入Application实例
     * @param url
     * @param target
     */
    public static void loadRoundImg(Context context, String url, ImageView target) {

        //https://github.com/wasabeef/glide-transformations--glide转换库
        GlideApp.with(context)
                .load(url)
                .circleCrop()//直接在链式中调用就行哦
                .transition(new DrawableTransitionOptions().crossFade(200))//渐显效果
                .into(target);
    }

    /**
     * 加载图片不需要缓存的
     *
     * @param c
     * @param url
     * @param target
     */
    public static void loadSourseImgWithNoCache(Context c, String url, ImageView target) {
        GlideApp.with(c)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(target);
    }

    /**
     * 根据资源ID加载图片
     *
     * @param c
     * @param resourceId
     * @param target
     * @param defaultId
     */
    public static void loadResourseImg(Context c, int resourceId, ImageView target, int defaultId) {
        GlideApp.with(c)
                .load(resourceId)
                .placeholder(defaultId)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .centerCrop()
                .into(target);
    }

    /**
     * 根据图片路径加载图片
     *
     * @param c
     * @param imgFile
     * @param target
     * @param defaultId
     */
    public static void loadFileImg(Context c, File imgFile, ImageView target, int defaultId) {
        GlideApp.with(c)
                .load(imgFile)
                .placeholder(defaultId)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .centerCrop()
                .into(target);
    }

    /**
     * 加载Gif为一张静态图片
     *
     * @param context
     * @param url
     */
    public static void LoadGiftAsBitmap(Context context, String url, ImageView imageView) {
        GlideApp.with(context).asBitmap().load(url).into(imageView);
    }

    /**
     * 你想只有加载对象是Gif时才能加载成功
     *
     * @param context
     * @param url
     */
    public static void LoadGiftAsGist(Context context, String url, ImageView imageView, int erroId) {
        GlideApp.with(context).asGif().load(url).error(erroId).into(imageView);

        //只加载一次gift图时调用
        //        GlideApp.with(context).load(url)
        //                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        //                .into(new GlideDrawableImageViewTarget(imageView,1));
    }

    /**
     * 加载缩略图,会自动与传入的fragment绑定生命周期,加载请求现在会自动在onStop中暂停在，onStart中重新开始。
     * 需要保证 ScaleType 的设置是正确的。
     *
     * @param fragment
     * @param url
     * @param imageView
     * @param scale     缩略图的比例
     */
    public static void LoadThumbNail(Fragment fragment, String url, ImageView imageView, float scale) {
        GlideApp.with(fragment).load(url).thumbnail(scale).into(imageView);
    }

    /**
     * 上传一张大小为xPx*yPx像素的用户头像的图片bytes数据
     *
     * @param context
     * @param url
     * @param xPx
     * @param yPx
     */
    public static void decodeResorse(Context context, File url, int xPx, int yPx) {
        GlideApp
                .with(context)
                .load(url)
                .into(new SimpleTarget<Drawable>(xPx, yPx) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        //上传动作
                    }
                });
    }

    /**
     * 显示本地视频(网络视频无效)
     *
     * @param context
     * @param filePath
     * @param imageView
     */
    public static void LoadShowLocalVidio(Context context, String filePath, ImageView imageView) {
        GlideApp.with(context).load(Uri.fromFile(new File(filePath))).into(imageView);
    }

    /**
     * 在通知栏中显示从网络上请求的图片
     *
     * @param context
     * @param remoteViews
     * @param viewId
     * @param notification
     * @param notificationId
     * @param url
     */
    public static void ShowImgInNotification(Context context, RemoteViews remoteViews, int viewId, Notification
            notification, int notificationId, String url) {
        NotificationTarget target = new NotificationTarget(context, viewId, remoteViews, notification, notificationId);
        GlideApp.with(context.getApplicationContext()).asBitmap().load(url).into(target);
    }

    /**
     * 下载图片,耗时操作不能放在主线程中进行
     *
     * @param context
     * @param url
     */
    public static void downLoadImage(Context context, String url, RequestListener<Bitmap> listener) {

        if (listener == null) {
            listener = new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean
                        isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource
                        dataSource, boolean isFirstResource) {
                    return false;
                }
            };
        }
        try {
            GlideApp.with(context).asBitmap().load(url).centerCrop().listener(listener).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public void clearCache(final Context context) {
        clearMemoryCache(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                clearDiskCache(context);
            }
        }).start();
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(Context context) {
        GlideApp.get(context).clearDiskCache();
    }
}
