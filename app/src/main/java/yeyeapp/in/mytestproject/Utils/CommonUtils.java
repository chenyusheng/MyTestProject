package yeyeapp.in.mytestproject.Utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.lzy.imagepicker.view.SystemBarTintManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import yeyeapp.in.mytestproject.R;
import yeyeapp.in.mytestproject.listener.OnStringHasUniqueListener;

/**
 * <pre>
 *     author : mac
 *     e-mail : 465680056@qq.com
 *     time   : 2017/06/29
 *     desc   :平常自己编写的一些 func ，实用的
 *     version: 1.0
 *
 * </pre>
 */
public class CommonUtils {

    //检查是否有特殊符号
    public static void checkHasUniqueStr(String s, OnStringHasUniqueListener l) {
        String strs = "~`$&%?？》《】【{}\"\"、￥@#*＋×÷㏒㏑∑∏×√∫∮∞∧≈≠≤≥≦≧≮≯½％‰ΘΣΨΩαβγδηθιτσπονλφχψω";
        for (int i = 0; i < strs.length(); i++) {
            if (s.contains(String.valueOf(strs.charAt(i)))) {
                //找到特殊字符
                l.onStringChange(true, String.valueOf(strs.charAt(i)));
                return;
            }
        }
        l.onStringChange(false, "");
        return;
    }

    //延时打开键盘
    public static void openKeyboardDelay(final EditText et, int MILLISECONDS) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (et != null) {
                    et.requestFocus();
                    KeyboardUtils.showSoftInput(et);
                }
            }
        }, MILLISECONDS);
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Activity activity) {
        SystemBarTintManager s = new SystemBarTintManager(activity);
        return s.getConfig().getStatusBarHeight();
    }

    //保留两位有效数字
    public static String getFormatDouble2Str(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    public static double getFormatDouble(double d) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN  四舍五入：UP
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.DOWN);
        return bg.doubleValue();
    }

    public static String getFormatDouble2Str(String s) {
        double d = 0;
        try {
            d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    // uri转文件路径
    public static String getPathFromUri(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    // 计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        try {
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width
                        / (float) reqWidth);
                inSampleSize = heightRatio > widthRatio ? heightRatio
                        : widthRatio;
            }
            return inSampleSize;
        } catch (Throwable e) {
            MyLog.e(e);
        }
        return 1;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 200, 200);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Throwable e) {
            MyLog.e(e);
        }
        return null;
    }

    // 根据Bitmap获得图片并压缩，返回bitmap用于显示
    public static Bitmap createBitmapThumbnail(Bitmap bitMap, int size) {
        MyLog.e("Util_common createBitmapThumbnail ----");
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = size;
        int newHeight = size;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        return newBitMap;
    }


    //uri转byteArray
    public static byte[] getbyteArrayFromUri(Context c, Uri uri) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    FileInputStream fis = new FileInputStream(new File(uri));
        InputStream fis = null;
        try {
            fis = c.getContentResolver().openInputStream(uri);
            byte[] buff = new byte[1024];
            int n;
            while (-1 != (n = fis != null ? fis.read(buff) : 0)) {
                bos.write(buff, 0, n);
            }
            byte[] b = bos.toByteArray();
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* 根据文件路径转换为bitmap */
    public static Bitmap getBitemapFromFile(String fileName) {
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inSampleSize = 8;
            return BitmapFactory.decodeFile(fileName, opt);
        } catch (Exception ex) {
            return null;
        }
    }

    //bitmap转byteAreay
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
            return baos.toByteArray();
        }
        return null;
    }

    // //把bitmap转换成InputStream
    public static InputStream bitmapToInputStream(Bitmap bm) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
            return isBm;
        } catch (Throwable e) {
            MyLog.e(e);
        }
        return null;
    }

    // 把bitmap转换成文件并返回文件uri
    public static Uri saveBitmap(Context context, Bitmap bm, int quality) {
        try {

            File f = new File(context.getCacheDir() +
                    "/head.jpg");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            FileOutputStream fOut;
            fOut = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
            fOut.flush();
            fOut.close();
            return Uri.fromFile(f);
        } catch (Throwable e) {
            MyLog.e(e);
        }
        return null;
    }

    //图像uri转换为文件uri
    public static Uri convertUri(Context context, Uri uri) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(is);

            return saveBitmap(context, b, 70);
        } catch (Exception e) {
            MyLog.e(e);
        }

        return null;
    }

    // dp转换px
    public static int dp2px(Context context, int dp) {
        if (context != null && context.getResources() != null && context.getResources().getDisplayMetrics() != null) {
            final float scale = context.getResources().getDisplayMetrics().density;
            int px = (int) (dp * scale + 0.5f);
            return px;
        } else {
            return 0;
        }
    }

    // sp转px
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 跳转到拨号界面
    public static void callTel(Context context, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // 获取icon font字体
    private static Typeface mIconFont;

    public static Typeface getIconFont(Context context) {
        if (mIconFont == null) {
            mIconFont = Typeface.createFromAsset(context.getAssets(),
                    "iconfont.ttf");
        }
        return mIconFont;
    }
}
