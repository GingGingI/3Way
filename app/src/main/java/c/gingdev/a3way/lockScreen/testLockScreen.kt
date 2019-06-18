package c.gingdev.a3way.lockScreen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import c.gingdev.a3way.R

class testLockScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flagInit()
        setContentView(R.layout.activity_lock_screen)
    }

    override fun onResume() {
        super.onResume()
        Log.e("lifeCycle", "resume")
//        ThreadStart
    }

    override fun onPause() {
        super.onPause()
        Log.e("lifeCycle", "pause")
//        ThreadClear
    }

    @SuppressLint("NewApi")
    private fun flagInit() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.run {
            decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_FULLSCREEN)

            setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) setShowWhenLocked(true)
            else addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun Any?.notNull(f: () -> Unit) {
        if (this != null) f()
    }
    private fun Any?.isNull(f: () -> Unit) {
        if (this == null) f()
    }
}