package link.standen.michael.slideshow.strategy.image;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import link.standen.michael.slideshow.model.FileItem;

/**
 * An interface for handling image loading strategies.
 */
public interface ImageStrategy {

	void setContext(Context context);

	void setCallback(ImageStrategyCallback callback);

	/**
	 * Preloads the image of the file item into a cache.
	 */
	void preload(FileItem item);

	/**
	 * Loads the image of the file item into the view.
	 */
	void load(FileItem item, ImageView view);

	/**
	 * Load relevant preferences.
	 */
	void loadPreferences(SharedPreferences preferences);

	interface ImageStrategyCallback {

		void updateImageDetails(FileItem item);

		/**
		 * Updates the image details for the file item.
		 * This method provides the width and height so they do not need to be recalculate.
		 */
		void updateImageDetails(FileItem item, int width, int height);

		/**
		 * Restarts the timer for the long loading warning snackbar.
		 */
		void beginLoadingSnackbar();

		/**
		 * Clears the timer for the long loading warning snackbar.
		 */
		void clearLoadingSnackbar();

		/**
		 * Queues the next slide using the default duration.
		 */
		void queueSlide();

		/**
		 * Queues the next slide using the given duration.
		 */
		void queueSlide(int duration);
	}

}
