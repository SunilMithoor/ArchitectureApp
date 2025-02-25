package com.sunil.snackbar.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Dimension


/**
 * Extension function to converta Float value from Density-Independent Pixels (DP) to actual pixels (PX).
 *
 * This function provides a convenient way to handle density-independent sizing in Android layouts.
 * It takes a float value representing DP and converts it to the corresponding pixel value based on
 * the device's displaymetrics.
 *
 * @param context The application context, used to access display metrics.
 * @return The pixel value equivalent to the input DP value.
 */
@Dimension(unit = Dimension.PX)
fun Float.dpToPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}

/**
 * Extension function to convert a Float value from Pixels (PX) to Density-Independent Pixels (DP).
 *
 *This function provides a convenient way to handle density-independent sizing in Android layouts.
 * It takes a float value representing PX and converts it to the corresponding DP value based on
 * the device's display metrics.
 *
 * @param context The application context, used to access display metrics.
 * @return The DP value equivalent to the input PX value.
 */
@Dimension(unit = Dimension.DP)
fun Float.pxToDp(context: Context): Float {
    val density = context.resources.displayMetrics.density
    return this / density
}
