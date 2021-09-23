package link.standen.michael.slideshow.util

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import link.standen.michael.slideshow.MainActivity
import timber.log.Timber


class AutostartOnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Timber.i("onReceive: ${intent.action}")
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {

            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}