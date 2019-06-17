package c.gingdev.a3way.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import c.gingdev.a3way.lockScreen.testLockScreen

import c.gingdev.a3way.lockScreenMode
import c.gingdev.a3way.lockScreenPrefName
import java.text.SimpleDateFormat
import java.util.*

class lockBroadcastReceiver: BroadcastReceiver() {

    private val TAG = "BroadcastReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        val pref = context?.getSharedPreferences(lockScreenPrefName, 0)
        val lockScreenMode = pref?.getBoolean(lockScreenMode, false) ?: false


        Log.i(TAG, "status : $lockScreenMode")

        /*
        * ACTION_SCREEN_ON -> 화면이 켜질때
        * ACTION_SCREEN_OFF -> 화면이 꺼질때
        * ACTION_BOOT_COMPLETED -> 부팅 완료시
        * */

        when(intent?.action) {
            Intent.ACTION_SCREEN_ON -> {
                Log.i(TAG, "On")
            }
            Intent.ACTION_SCREEN_OFF -> if (lockScreenMode) {
                val hour = getHour()
                Log.i(TAG, "Off  At $hour")

                val i = Intent(context, testLockScreen::class.java)
                    .apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    }
                context?.startActivity(i)
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.i(TAG, "BOOT")
            }
        }
    }

    private fun getHour(): Int =
        SimpleDateFormat("HH", Locale.KOREA).format(Date(System.currentTimeMillis())).toInt()
}