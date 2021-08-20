package link.standen.michael.slideshow

import android.app.Application
import timber.log.Timber

class SlideshowApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}