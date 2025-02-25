package com.sunil.app.presentation.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.sunil.app.R
import com.sunil.app.base.AppApplication
import com.sunil.app.base.BaseAppCompatActivity
import com.sunil.app.base.getAppName
import com.sunil.app.base.getAppVersion
import com.sunil.app.databinding.ActivityAboutBinding
import com.sunil.app.presentation.extension.viewBinding
import com.sunil.app.presentation.viewmodel.RestfulViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AboutActivity : BaseAppCompatActivity() {

    companion object {
        private const val TAG = "AboutActivity"
    }

    private val binding by viewBinding(ActivityAboutBinding::inflate)
    private val viewModel by viewModels<RestfulViewModel>()


    override fun layout() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val appNameTextView = findViewById<TextView>(R.id.appNameTextView)
        val versionTextView = findViewById<TextView>(R.id.versionTextView)
        val data = findViewById<TextView>(R.id.data)

        val appName = getAppName(this)
        val appVersion = getAppVersion(this)

        appNameTextView.text = appName
        versionTextView.text = appVersion

        viewModel.getAllData()
    }

    override fun observeLiveData() {

        viewModel.messageLiveData.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.observeGetAllData.observe(this) {
            Timber.tag(TAG).d("observeGetAllData $it")
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        }

    }
}
