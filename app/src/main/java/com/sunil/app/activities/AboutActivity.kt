package com.sunil.app.activities

import android.content.pm.PackageManager
import com.sunil.app.R

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sunil.app.BuildConfig
import com.sunil.app.base.getAppName
import com.sunil.app.base.getAppVersion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val appNameTextView = findViewById<TextView>(R.id.appNameTextView)
        val versionTextView = findViewById<TextView>(R.id.versionTextView)


        val appName = getAppName(this)
        val appVersion = getAppVersion(this)

        appNameTextView.text = appName
        versionTextView.text = appVersion
    }
}
