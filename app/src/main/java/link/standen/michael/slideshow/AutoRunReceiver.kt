package link.standen.michael.slideshow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import link.standen.michael.slideshow.util.Tools
import timber.log.Timber

class AutoRunReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("Autostarting app")

        val mainActivityIntent = Intent(context, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        context?.startActivity(mainActivityIntent)

        Tools.turnOn(context!!)
    }
}