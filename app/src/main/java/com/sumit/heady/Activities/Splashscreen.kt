package com.sumit.heady.Activities

import android.content.Intent
import android.os.Bundle
import com.sumit.heady.Base.BaseActivity
import com.sumit.heady.Constants.toImageLoader
import com.sumit.heady.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen for Application
 */
class Splashscreen : BaseActivity() {
    companion object {
        private val SPLASH_TIME_OUT = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        imgSplash.toImageLoader(R.drawable.loading)
        startHandler()
    }

    /**
     * GO to homepage after some delay
     */
    private fun startHandler() {
        GlobalScope.launch {
            delay(SPLASH_TIME_OUT)
            startActivity(Intent(this@Splashscreen, HomeActivity::class.java))
            finish()
        }
    }

}
