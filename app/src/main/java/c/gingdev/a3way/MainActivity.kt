package c.gingdev.a3way

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import c.gingdev.a3way.service.lockScreenService

class MainActivity : AppCompatActivity() {

    private val pref by lazy(LazyThreadSafetyMode.NONE)
    { getSharedPreferences(lockScreenPrefName, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService()

        pref.edit().apply { putBoolean(lockScreenMode, true) }.apply()
    }

    private fun startService() {
        val i = Intent(applicationContext, lockScreenService::class.java)
        startService(i)
    }
}
