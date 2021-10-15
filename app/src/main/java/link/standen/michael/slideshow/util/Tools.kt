package link.standen.michael.slideshow.util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import link.standen.michael.slideshow.AutoRunReceiver
import link.standen.michael.slideshow.AutoShutdownReceiver
import timber.log.Timber
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime


class Tools {

    companion object {

        fun turnOn(ctx: Context) {
            val pm = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.newWakeLock(PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "app:MyLock").acquire(100)
        }

        @ExperimentalTime
        fun setupWakeUp(activity: Activity) {

            // AlarmManager instance from the system services

            // Intent: this is responsible to prepare the android component what PendingIntent will start when the alarm is triggered. That component can be anyone (activity, service, broadcastReceiver, etc)
            // Intent to start the Broadcast Receiver
            val intent = Intent(activity, AutoRunReceiver::class.java)

            // PendingIntent: this is the pending intent, which waits until the right time, to be called by AlarmManager
            // The Pending Intent to pass in AlarmManager
            val pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                getCalendarInMillisWithSetTime(DurationUnit.HOURS.toMillis(10) + DurationUnit.MINUTES.toMillis(0)),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent
            )
        }

        @ExperimentalTime
        fun setupClosingApp(activity: Activity) {

            val intent = Intent(activity, AutoShutdownReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                getCalendarInMillisWithSetTime(DurationUnit.HOURS.toMillis(10) + DurationUnit.MINUTES.toMillis(8)),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                pendingIntent
            )
        }

        private fun getCalendarInMillisWithSetTime(timeToSet: Long): Long {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis() + timeToSet
            }
            Timber.d("Time set: ${calendar.timeInMillis}")

            return calendar.timeInMillis
        }
    }

}