package com.sunil.app.data.utils

object Constants {
    const val PREF = "MyAppPref"
    const val USER_TOKEN = "user_token"
    const val USER_LOGGED_IN = "user_logged_in"
    const val FIRST_TIME_LAUNCH = "first_time_launch"
}

object HttpClient {
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    const val CONNECTION_TIME_OUT_MLS = CONNECT_TIMEOUT * 1000L
    const val CLIENT_TIME_OUT = 30L
}

object Authentication {
    const val MAX_RETRY = 1
}

object AppConstants {
    const val USER = "user"
    const val USER_DATA = "user_data"
    const val EMPTY = ""
    const val DARK_MODE = "dark_mode"
}
