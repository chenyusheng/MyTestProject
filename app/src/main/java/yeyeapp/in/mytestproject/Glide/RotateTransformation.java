package yeyeapp.in.mytestproject.Glide;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by wangfei on 2016/6/21 18:25.
 * 让图片旋转X度的转换类
 */
public class RotateTransformation extends BitmapTransformation {
    private float rotateRotationAngle = 0f;
    private static final String ID = "yeyeapp.in.mytestprohect.glide.RotateTransformation";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    public RotateTransformation( float rotateRotationAngle) {

        this.rotateRotationAngle = rotateRotationAngle;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();

        matrix.postRotate(rotateRotationAngle);

        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RotateTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}