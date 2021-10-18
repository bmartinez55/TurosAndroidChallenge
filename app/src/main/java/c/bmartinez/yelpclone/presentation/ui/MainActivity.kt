package c.bmartinez.yelpclone.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import c.bmartinez.yelpclone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}