package link.standen.michael.slideshow.strategy.image;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import link.standen.michael.slideshow.R;
import link.standen.michael.slideshow.model.FileItem;
import link.standen.michael.slideshow.strategy.image.glide.GlideRotateDimenTransformation;
import timber.log.Timber;

public class GlideImageStrategy implements ImageStrategy {

    private static final String TAG = GlideImageStrategy.class.getName();

	private Context context;
	private ImageStrategyCallback callback;

	private static boolean PLAY_GIF;
	private static boolean AUTO_ROTATE_DIMEN;

	@Override
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void setCallback(ImageStrategyCallback callback) {
		this.callback = callback;
	}

	@Override
	public void preload(final FileItem item) {

		if (PLAY_GIF) {
			return;
		}

		Glide.with(context)
			.asBitmap()
			.load(item.getPath()).preload();
	}

	@Override
	public void load(final FileItem item, final ImageView view) {

		RequestOptions requestOptions = RequestOptions
				.diskCacheStrategyOf(DiskCacheStrategy.DATA)
				.dontAnimate()
				.placeholder(view.getDrawable());

		if (PLAY_GIF) {
			return;
		}

		if (AUTO_ROTATE_DIMEN) {
			requestOptions = requestOptions.transform(new GlideRotateDimenTransformation());
		} else {
			requestOptions = requestOptions.fitCenter();
		}

		Glide.with(context)
				.setDefaultRequestOptions(requestOptions)
				.asBitmap()
				.load(item.getPath())
				.error(R.color.image_background)
				.listener(new RequestListener<Bitmap>() {
					@Override
					public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
						Timber.e(e, "Error loading image");
						callback.clearLoadingSnackbar();
						callback.queueSlide();
						callback.updateImageDetails(item);
						return false;
					}

					@Override
					public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
						callback.clearLoadingSnackbar();
						callback.queueSlide();
						callback.updateImageDetails(item);

						return false;
					}

				})
				.into(view);
	}

	@Override
	public void loadPreferences(SharedPreferences preferences) {
		PLAY_GIF = false;
		AUTO_ROTATE_DIMEN = preferences.getBoolean("auto_rotate_dimen", false);
	}
}
