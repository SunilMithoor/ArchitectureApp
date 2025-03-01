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


/**
 * Interceptor to add common headers to all requests.
 *
 * This interceptor adds headers like User-Agent, API-Key, Authorization, and app_version
 * based on the URL and user login status.
 *
 * @author Sunil
 * @version 1.0
 * @since 2025-01-28
 */
class UserAgentInterceptor @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences,
    @ApplicationContext private val context: Context
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

    /**
     * The User-Agent string to be added to the headers.
     * It's built using the [buildUserAgent] function and the application context.
     */
    private val userAgent: String by lazy {
        buildUserAgent(context)
    }

    /**
     * Intercepts the request and adds the necessary headers.
     *
     * @param chain The [Interceptor.Chain] to proceed with the request.
     * @return The [Response] after adding the headers.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Always add User-Agent
        requestBuilder.header(USER_AGENT_HEADER, userAgent)

        val urlString = originalRequest.url.toString()
        // Get user login status and token from shared preferences
        val isUserLoggedIn = appSharedPreferences.getValue(USER_LOGGED_IN, Boolean::class)
            ?: false
        val userToken =
            appSharedPreferences.getValue(USER_TOKEN, String::class)

        // Add headers based on URL and user login status
        when {
            urlString.contains(RESTFUL_ENDPOINT) -> {
                requestBuilder.header(API_KEY_HEADER, "")
            }

            urlString.contains(JSON_PLACEHOLDER_ENDPOINT) -> {
                if (isUserLoggedIn) {
                    Timber.tag(TAG).d("token: $userToken")
                    userToken?.let {
                        requestBuilder.header(
                            AUTHORIZATION_HEADER,
                            BEARER_PREFIX + it
                        )
                    } // Handle null token
                    requestBuilder.header(APP_VERSION_HEADER, BuildConfig.VERSION_NAME)
                }
            }

            else -> {
                requestBuilder.header(APP_VERSION_HEADER, BuildConfig.VERSION_NAME)
                if (isUserLoggedIn) {
                    Timber.tag(TAG).d("token: $userToken")
                    userToken?.let {
                        requestBuilder.header(
                            AUTHORIZATION_HEADER,
                            BEARER_PREFIX + it
                        )
                    } // Handle null token
                }
            }
        }

        // Log API call details
        Timber.tag(TAG).d("API call: ${originalRequest.url}")
        Timber.tag(TAG).d("logged in: $isUserLoggedIn")

        return chain.proceed(requestBuilder.build())
    }
}

/**
 * Authenticator to handle unauthorized (401) responses.
 *
 * This authenticator logs out the userand stops further request attempts when a 401 response is received.
 */
class TokenAuthenticator @Inject constructor(
    private val appSharedPreferences: AppSharedPreferences,
    @ApplicationContext private val context: Context
) : Authenticator {

    companion object {
        private const val TAG = "TokenAuthenticator"
        private const val UNAUTHORIZED_CODE = 401
    }

    /**
     * Authenticates the request when an unauthorized response is received.
     *
     * @param route The route of the request.
     * @param response The unauthorized response.
     * @return null to stop further request attempts.
     * @throws IOException If there is an I/O error.
     */
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

    /**
     * Handles the unauthorized response by showing a toast message and logging out the user.
     */
    private fun handleUnauthorizedResponse() {
        // Show a toast message
        showToast("Unauthorized. Logging out...")

        // Logout user and clear session
        appSharedPreferences.logoutUser()
    }

    /**
     * Shows a toast message on the main thread.
     *
     * @param message The message to be shown in the toast.
     */
    private fun showToast(message: String) {
        // Ensure toast is shown on the main thread
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
