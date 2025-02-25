package com.sunil.snackbar

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes


/**
 * Represents an attribute that can be applied to an AirySnackBar.
 */
sealed interface AirySnackBarAttribute

/**
 * Represents an attribute that affects the layout of an AirySnackBar.
 */
sealed interface AirySnackBarLayoutAttribute : AirySnackBarAttribute

/**
 * Attributes related to the icon displayed in the AirySnackBar.
 */sealed class IconAttribute : AirySnackBarAttribute {
    /**
     * Specifies the icon to be displayed using a drawable resource.
     *
     * @property iconRes The drawable resource ID for the icon.
     */
    data class Icon(@DrawableRes val iconRes: Int) : IconAttribute()

    /**
     * Specifies the color tint for the icon.
     *
     * @property iconTint The color resource ID for the icon tint.
     * @property forceTintColor If true, the tint color will be applied even if the icon already has a color.
     */data class IconTint(
        @ColorRes val iconTint: Int,
        val forceTintColor: Boolean = false
    ) : IconAttribute()

    /**
     * Indicates that no icon should be displayed.
     */
    data object NoIcon : IconAttribute()
}

/**
 * Attributes related to the text displayed in the AirySnackBar.
 */
sealed class TextAttribute : AirySnackBarAttribute {
    /**
     * Specifies the text content to be displayed.
     *
     * @property text The text string.
     */
    data class Text(val text: String) : TextAttribute()

    /**
     * Specifies the color of the text.
     *
     * @property textColor The color resource ID for the text.
     * @property forceTextColor If true, the text color will be applied even if the text already has a color.
     */
    data class TextColor(
        @ColorRes val textColor: Int,
        val forceTextColor: Boolean = false
    ) : TextAttribute()

    /**
     * Specifies the size of the text.
     *
     * @property size The text size in SP (scale-independent pixels).
     */
    data class TextSize(val size: Float) : TextAttribute()
}

/**
 * Attributes related to the corner radius of the AirySnackBar.
 */
sealed class RadiusAttribute : AirySnackBarAttribute {
    /**
     * Specifies the corner radius.
     *
     * @property radius The radius value in DP (density-independent pixels).
     */
    data class Radius(val radius: Float) : RadiusAttribute()
}

/**
 * Attributes related to the gravity (position) of the AirySnackBar.
 */
sealed class GravityAttribute : AirySnackBarLayoutAttribute {
    /**
     * Indicates that the AirySnackBar should be displayed at the top of the screen.
     */
    data object Top : GravityAttribute()

    /**
     * Indicates that the AirySnackBar should be displayed at the bottom of the screen.
     */
    data object Bottom : GravityAttribute()
}

/**
 * Attributes related to the animation of the AirySnackBar.
 */
sealed class AnimationAttribute : AirySnackBarLayoutAttribute {
    /**
     * Indicates a slide-in/slide-out animation.
     */
    data object SlideInOut : AnimationAttribute()

    /**
     * Indicates a fade-in/fade-out animation.
     */
    data object FadeInOut : AnimationAttribute()
}

/**
 * Attributes related to the size and spacing of the AirySnackBar.
 */
sealed class SizeAttribute : AirySnackBarLayoutAttribute {
    /**
     * Specifies the margins around the AirySnackBar.
     *
     * @property left The left margin.
     * @property top The top margin.
     * @property right The right margin.
     * @property bottom The bottom margin.
     * @property unit The unit of measurement for the margins (DP or PX).
     */
    data class Margin(
        val left: Int = 0,
        val top: Int = 0,
        val right: Int = 0,
        val bottom: Int = 0,
        val unit: SizeUnit = SizeUnit.DP
    ) : SizeAttribute()

    /**
     * Specifies the padding inside the AirySnackBar.
     *
     * @property left The left padding.
     * @property top The top padding.
     * @property right The right padding.
     * @property bottom The bottom padding.* @property unit The unit of measurement for the padding (DP or PX).
     */
    data class Padding(
        val left: Int = 0,
        val top: Int = 0,
        val right: Int = 0,
        val bottom: Int = 0,
        val unit: SizeUnit = SizeUnit.DP
    ) : SizeAttribute()
}

/**
 * Represents the unit of measurement for size attributes.
 */
enum class SizeUnit {
    /**
     * Density-independent pixels.
     */
    DP,

    /**
     * Pixels.
     */
    PX
}
