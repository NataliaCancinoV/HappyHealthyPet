package uv.tc.happyhealthypet
import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        playAlarmSound(context)
        createNotificationChannel(context)
        createNotification(context)
    }

    private fun playAlarmSound(context: Context) {
        var alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmSound == null) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmSound)
        ringtone?.play()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Alarm Notification"
            val description = "Notification for Alarm"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(AlarmReceiver.Companion.CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(context: Context) {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, AlarmReceiver.Companion.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Alarma")
                .setContentText("Esto es un recordatorio")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)
        if (notificationManager != null) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(AlarmReceiver.Companion.NOTIFICATION_ID, builder.build())
        } else {
            Log.e("AlarmReceiver", "NotificationManager es nula.")
        }
    }

    companion object {
        private const val CHANNEL_ID = "alarm_channel"
        private const val NOTIFICATION_ID = 1
    }
}