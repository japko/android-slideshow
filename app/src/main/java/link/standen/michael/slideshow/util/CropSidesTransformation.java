package link.standen.michael.slideshow.util;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;


public class CropSidesTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID = "jp.wasabeef.glide.transformations.CropTransformation." + VERSION;

    private int width;
    private int height;

    public CropSidesTransformation(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        width = width == 0 ? toTransform.getWidth() : width;
        height = height == 0 ? toTransform.getHeight() : height;

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        bitmap.setHasAlpha(true);

        float scale = 1.0f;

        if((width <= height && toTransform.getWidth() <= toTransform.getHeight())||
                (width > height && toTransform.getWidth() > toTransform.getHeight())) {

            float scaleX = (float) width / toTransform.getWidth();
            float scaleY = (float) height / toTransform.getHeight();
            scale = Math.max(scaleX, scaleY);

        }

        float scaledWidth = scale * toTransform.getWidth();
        float scaledHeight = scale * toTransform.getHeight();
        float left = (width - scaledWidth) / 2;
        float top = (height - scaledHeight) / 2;
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);


        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(toTransform, null, targetRect, null);

        return bitmap;
    }


    @Override
    public String toString() {
        return "CropTransformation(width=" + width + ", height=" + height + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CropSidesTransformation &&
                ((CropSidesTransformation) o).width == width &&
                ((CropSidesTransformation) o).height == height;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + width * 100000 + height * 1000;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + width + height).getBytes(CHARSET));
    }
}