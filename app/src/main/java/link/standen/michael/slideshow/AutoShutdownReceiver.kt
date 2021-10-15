package link.standen.michael.slideshow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AutoShutdownReceiver : BroadcastReceiver() {

    companion object {
        const val PARAM_KILL_APP = "kill_app"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.i("Closing app")

        val mainActivityIntent = Intent(context, MainActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        mainActivityIntent.putExtra(PARAM_KILL_APP, true)
        context?.startActivity(mainActivityIntent)
    }
}