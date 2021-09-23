package link.standen.michael.slideshow.strategy.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

import timber.log.Timber;

/**
 * Rotates an image if the image is wider than it is high.
 * Note: This class uses {@link com.bumptech.glide.load.resource.bitmap.TransformationUtils#fitCenter(BitmapPool, Bitmap, int, int)}  to reduce computation.
 */
public class GlideRotateDimenTransformation extends BitmapTransformation {

	private static final String TAG = GlideRotateDimenTransformation.class.getName();

	public GlideRotateDimenTransformation() {
		super();
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Timber.d("Height: %d Width: %d", toTransform.getHeight(), toTransform.getWidth());
		if (toTransform.getHeight() >= toTransform.getWidth()){
			// Perform fit center here on un-rotated image.
			toTransform = TransformationUtils.fitCenter(pool, toTransform, outWidth, outHeight);
			return toTransform;
		}
		// Fit center using largest side (width) for both to reduce computation for rotate
		//noinspection SuspiciousNameCombination
		toTransform = TransformationUtils.fitCenter(pool, toTransform, outWidth, outWidth);
		return TransformationUtils.rotateImage(toTransform, 90);
	}


	@Override
	public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

	}
}
