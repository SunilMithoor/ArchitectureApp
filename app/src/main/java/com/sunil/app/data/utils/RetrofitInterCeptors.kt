package com.sunil.app.data.utils


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.sunil.app.BuildConfig
import com.sunil.app.data.local.sharedprefs.AppSharedPreferences
import com.sunil.app.data.utils.Constants.USER_LOGGED_IN
import com.sunil.app.data.utils.Constants.USER_TOKEN
import com.sunil.app.data.utils.UserAgentBuilder.buildUserAgent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okio.IOException
import timber.log.Timber
import javax.inject.Inject


class UserAgentInterceptor @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences,
    private val context: Context?
) : Interceptor {

    companion object {
        private const val TAG = "UserAgentInterceptor"
        private const val USER_AGENT_HEADER = "User-Agent"
        private const val API_KEY_HEADER = "api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val APP_VERSION_HEADER = "app_version"
        private const val BEARER_PREFIX = "Bearer "
        private const val RESTFUL_ENDPOINT = "restful"
        private const val JSON_PLACEHOLDER_ENDPOINT = "jsonplaceholder"
    }

    private val userAgent: String by lazy {
        context?.let { buildUserAgent(it) } ?: "Unknown"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Always add User-Agent
        requestBuilder.header(USER_AGENT_HEADER, userAgent)

        val urlString = originalRequest.url.toString()
        val userLoggedIn = appSharedPreferences.getValue(USER_LOGGED_IN, Boolean::class.java)
        val userToken = appSharedPreferences.getValue(USER_TOKEN, String::class.java)

        // Add headers based on URL and user login status
        when {
            urlString.contains(RESTFUL_ENDPOINT) -> {
                requestBuilder.header(API_KEY_HEADER, "")
            }

            urlString.contains(JSON_PLACEHOLDER_ENDPOINT) -> {
                if (userLoggedIn == true) {
                    Timber.tag(TAG).d("token $userToken")
                    requestBuilder.header(AUTHORIZATION_HEADER, BEARER_PREFIX + userToken)
                    requestBuilder.header(APP_VERSION_HEADER, BuildConfig.VERSION_NAME)
                }
            }

            else -> {
                requestBuilder.header(APP_VERSION_HEADER, BuildConfig.VERSION_NAME)
                if (userLoggedIn == true)  {
                    Timber.tag(TAG).d("token $userToken")
                    requestBuilder.header(AUTHORIZATION_HEADER, BEARER_PREFIX + userToken)
                }
            }
        }

        // Log API call details
        Timber.tag(TAG).d("API call")
        Timber.tag(TAG)
            .d("logged in ${appSharedPreferences.getValue(USER_LOGGED_IN, Boolean::class.java)}")
        Timber.tag(TAG).d("request.url-->${originalRequest.url}")

        return chain.proceed(requestBuilder.build())
    }
}


class TokenAuthenticator @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences,
    @ApplicationContext private val context: Context
) : Authenticator {

    companion object {
        private const val TAG = "TokenAuthenticator"
        private const val UNAUTHORIZED_CODE = 401
    }

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        // Check if the response is unauthorized
        if (response.code == UNAUTHORIZED_CODE) {
            Timber.tag(TAG).d("Unauthorized response received (401). Logging out user.")
            handleUnauthorizedResponse()
            return null // Stop further request attempts
        }
        return null // Continue with the request if not unauthorized
    }

    private fun handleUnauthorizedResponse() {
        // Show a toast message
        showToast("Unauthorized. Logging out...")

        // Logout user and clear session
        appSharedPreferences.logoutUser()
    }

    private fun showToast(message: String) {
        // Ensure toast is shown on the main thread
        context?.let { _ ->
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
