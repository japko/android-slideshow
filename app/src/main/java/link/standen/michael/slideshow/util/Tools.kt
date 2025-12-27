package link.standen.michael.slideshow.util

import android.content.Context
import android.os.PowerManager


class Tools {

    companion object {

        fun turnOn(ctx: Context) {
            val pm = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:MyLock").acquire(100)
        }

    }

}