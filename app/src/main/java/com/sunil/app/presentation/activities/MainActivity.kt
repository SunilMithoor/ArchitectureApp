//package com.sunil.app.presentation.activities
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.result.ActivityResultLauncher
//import androidx.appcompat.app.AppCompatActivity
//import com.sunil.app.R
//import com.sunil.app.presentation.extension.registerStartActivityForResult
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var myLauncher: ActivityResultLauncher<Intent>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Register the launcher
//        myLauncher = registerStartActivityForResult<AboutActivity> { resultCode, data ->
//            // Handle the result here
//            if (resultCode == RESULT_OK) {
//                val resultData = data?.getStringExtra("result_key")
//                // Do something with the result
//            }
//        }
//
//        // Example usage with shared element
//        val myView: View = findViewById(R.id.myView)
//        myLauncher = registerStartActivityForResult<AboutActivity>(sharedElement = myView to "transitionName") { resultCode, data ->
//            // Handle the result here
//            if (resultCode == RESULT_OK) {
//                val resultData = data?.getStringExtra("result_key")
//                // Do something with the result
//            }
//        }
//
//        // Example usage with intent configuration
//        myLauncher = registerStartActivityForResult<AboutActivity>(intentBuilder = {
//            putExtra("key", "value")
//        }) { resultCode, data ->
//            // Handle the result here
//            if (resultCode == RESULT_OK) {
//                val resultData = data?.getStringExtra("result_key")
//                // Do something with the result
//            }
//        }
//
//        // Example usage with shared element and intent configuration
//        myLauncher = registerStartActivityForResult<AboutActivity>(sharedElement = myView to "transitionName", intentBuilder = {
//            putExtra("key", "value")
//        }) { resultCode, data ->
//            // Handle the result here
//            if (resultCode == RESULT_OK) {
//                val resultData = data?.getStringExtra("result_key")
//                // Do something with the result
//            }
//        }
//
//        //Launch the activity
//        myLauncher.launch(Intent())
//    }
//}
