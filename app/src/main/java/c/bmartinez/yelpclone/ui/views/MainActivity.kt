package c.bmartinez.yelpclone.ui.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import c.bmartinez.yelpclone.R

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF0000")))

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frag_container, MainFragment()).commit()
    }

}