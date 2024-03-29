package c.gingdev.a3way.service

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import c.gingdev.a3way.R
import c.gingdev.a3way.broadcast.lockBroadcastReceiver

class lockScreenService: Service() {

    private val NOTIFICATION_ID = 0

    private val lockReceiver: BroadcastReceiver
            by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { lockBroadcastReceiver() }

    override fun onCreate() {
        super.onCreate()
        initFilter()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForeground()
        else startForeground(NOTIFICATION_ID, Notification())

        intent notNull { initFilter() }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(lockReceiver)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            stopForeground(true)
    }

    private fun initFilter() {
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(lockReceiver, filter)
    }

//    Android Oreo부터 서비스에 채널을 설정해줘야함.
    @TargetApi(Build.VERSION_CODES.O)
    private fun startForeground() {
        val Channel_ID = "TEST_LOCKSCREEN_CHANNEL"
        val Channel_NAME = "TEST LockScreen"

        val channel = NotificationChannel(
            Channel_ID,
            Channel_NAME,
            NotificationManager.IMPORTANCE_MIN)
            .apply {
                lightColor = R.color.colorPrimary
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }

        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(this, Channel_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private infix fun <T: Any?> T?.notNull(f: T?.() -> Unit) {
        if(this != null) f()
    }
}