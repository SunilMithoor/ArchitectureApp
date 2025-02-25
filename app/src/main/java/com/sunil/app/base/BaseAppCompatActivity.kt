package com.sunil.app.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.sunil.app.R
import com.sunil.app.presentation.module.Navigator
import com.sunil.app.databinding.ActivityBaseBinding
import com.sunil.app.presentation.extension.castAs
import com.sunil.app.presentation.extension.gone
import com.sunil.app.presentation.extension.hideKeyboard
import com.sunil.app.presentation.extension.visible
import com.sunil.snackbar.AirySnackBar
import com.sunil.snackbar.AirySnackBarSource
import com.sunil.snackbar.AnimationAttribute
import com.sunil.snackbar.GravityAttribute
import com.sunil.snackbar.IconAttribute
import com.sunil.snackbar.RadiusAttribute
import com.sunil.snackbar.SizeAttribute
import com.sunil.snackbar.SizeUnit
import com.sunil.snackbar.TextAttribute
import com.sunil.snackbar.Type
import timber.log.Timber
import javax.inject.Inject

/**
 * Base Activity class for the app
 *
 */
abstract class BaseAppCompatActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "BaseAppCompatActivity"
    }

    @Inject
    lateinit var navigator: Navigator

    private val binding by lazy {
        ActivityBaseBinding.inflate(layoutInflater).apply {
            containerView.addView(layout())
        }
    }

    val navController by lazy {
        //Navigation.findNavController(this, R.id.containerHostFragment)
        navHost().navController
    }


    val bundle by lazy {
        intent.extras ?: Bundle()
    }

    /***
     *  Add UI View
     */
    abstract fun layout(): View

    /**
     *  Observe LiveData/Flow
     */
    open fun observeLiveData() {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.root.hideKeyboard()
        observeLiveData()
    }

    override fun onPause() {
        super.onPause()
        binding.root.hideKeyboard()
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            navController.navigateUp()
        } else {
            super.onBackPressed()
        }
    }

    fun showLoader() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        binding.progress.visible()
    }

    fun hideLoader() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.progress.gone()
    }

    fun snackBar(msg: String) {
//        binding.root.snackBar(msg)
    }

    fun snackBar(@StringRes resID: Int) {
//        binding.root.snackBar(resID)
    }

    /*fun changeGraph(@NavigationRes resId: Int) {
        val graph = navController.navInflater.inflate(resId)
        graph.startDestination = R.id.otpFragment
        navController.graph = graph
    }*/


    fun showAlertMessage(message: String) {
        try {
            AirySnackBar.make(
                source = AirySnackBarSource.FromActivity(activity = this),
                type = Type.Custom(bgColor = R.color.bg_snackbar),
                attributes =
                listOf(
                    TextAttribute.Text(text = message),
                    TextAttribute.TextColor(textColor = R.color.white),
                    TextAttribute.TextSize(size = 16f),
                    IconAttribute.NoIcon,
                    SizeAttribute.Margin(left = 0, right = 0, unit = SizeUnit.DP),
                    SizeAttribute.Padding(
                        top = 16,
                        bottom = 16,
                        right = 16,
                        left = 16,
                        unit = SizeUnit.DP
                    ),
                    RadiusAttribute.Radius(radius = 0f),
                    GravityAttribute.Bottom,
                    AnimationAttribute.FadeInOut
                )
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.tag(TAG).e(e)
        }
    }


}



fun AppCompatActivity.showLoader() {
    castAs<BaseAppCompatActivity> {
        showLoader()
    }
}

fun AppCompatActivity.hideLoader() {
    castAs<BaseAppCompatActivity> {
        hideLoader()
    }
}

fun AppCompatActivity.snackBar(msg: String) {
    castAs<BaseAppCompatActivity> {
        snackBar(msg)
    }
}

fun AppCompatActivity.snackBar(@StringRes resID: Int) {
    castAs<BaseAppCompatActivity> {
        snackBar(resID)
    }
}

fun AppCompatActivity.childFragments() =
    (supportFragmentManager.fragments.first() as? NavHostFragment)?.childFragmentManager?.fragments

fun AppCompatActivity.navHost() = supportFragmentManager.fragments.first() as NavHostFragment
