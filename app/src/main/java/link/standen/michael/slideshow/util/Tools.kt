package link.standen.michael.slideshow.util

import android.content.Context
import android.provider.Settings
import android.os.PowerManager


class Tools {

    fun turnOff(ctx: Context) {
        Settings.System.putInt(ctx.contentResolver, Settings.System.SCREEN_OFF_TIMEOUT, 1000)
    }

    fun turnOn(ctx: Context) {
        val pm = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isInteractive) {
            pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:MyLock").acquire(100)
        }
    }
}