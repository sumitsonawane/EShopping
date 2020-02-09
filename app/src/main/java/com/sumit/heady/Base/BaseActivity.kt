package com.sumit.heady.Base


import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.sumit.heady.R


open class BaseActivity : AppCompatActivity() {
    private var progressOverlay: FrameLayout? = null
    private var noOfTimeProgressVisible = 0

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setUpOverlayProgress()
    }


    protected fun setUpOverlayProgress() {
        val rootLayout = getRootView()
        rootLayout.post {
            progressOverlay = LayoutInflater.from(this@BaseActivity).inflate(
                R.layout.inc_progress_overlay,
                rootLayout,
                false
            ) as FrameLayout
            if (progressOverlay != null) {
                rootLayout.addView(progressOverlay, 0)
            }
        }
    }

    @Synchronized
    fun showProgress() {
        getRootView().post {
            if (progressOverlay != null) {
                noOfTimeProgressVisible++
                progressOverlay?.setVisibility(View.VISIBLE)
                progressOverlay?.bringToFront()
            }
        }
    }


    @Synchronized
    fun hideProgress() {
        if (progressOverlay != null && --noOfTimeProgressVisible <= 0) {
            progressOverlay?.visibility = View.GONE
        }
    }

    private fun getRootView(): ViewGroup {
        return window.decorView as ViewGroup
    }
}