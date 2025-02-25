package com.sunil.snackbar

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.sunil.snackbar.extensions.findSuitableParent


class AirySnackBar(
    parent: ViewGroup,
    private val content: AirySnackBarView,
    model: AirySnackBarModel
) : BaseTransientBottomBar<AirySnackBar>(parent, content, content) {

    private val defaultTopAndBottomMargin: Int
        get() = content.context.resources.getDimensionPixelSize(
            R.dimen.snackbar_top_and_bottom_margin
        )

    private val defaultVerticalPadding: Int
        get() = content.context.resources.getDimensionPixelSize(R.dimen.padding_small)

    private var airyGravity: Int = Gravity.TOP or Gravity.CENTER_HORIZONTAL

    init {
        initializeSnackBar(model)
    }

    private fun initializeSnackBar(model: AirySnackBarModel) {
        var margins = SizeAttribute.Margin()

        getView().apply {
            setBackgroundColor(
                ContextCompat.getColor(context, android.R.color.transparent)
            )

            model.snackBarLayoutAttribute.forEach { attr ->
                when (attr) {
                    is GravityAttribute.Top -> {
                        airyGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                    }

                    is GravityAttribute.Bottom -> {
                        airyGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    }

                    is AnimationAttribute.FadeInOut -> {
                        animationMode = ANIMATION_MODE_FADE
                    }

                    is AnimationAttribute.SlideInOut -> {
                        animationMode = ANIMATION_MODE_SLIDE
                    }

                    is SizeAttribute.Padding -> {
                        setContentPaddings(attr)
                    }

                    is SizeAttribute.Margin -> {
                        margins = attr
                    }
                }
            }

            model.view?.let { applyWindowInsets(it, margins) }

            setGravity()
        }
    }

    private fun applyWindowInsets(view: View, margins: SizeAttribute.Margin) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val navigationBarInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

            val densityFactor = if (margins.unit == SizeUnit.DP) {
                content.context.resources.displayMetrics.density
            } else {
                1f
            }

            updateBaseMargins(
                left = (margins.left * densityFactor).toInt(),
                top = statusBarInsets.top + (margins.top * densityFactor).toInt(),
                right = (margins.right * densityFactor).toInt(),
                bottom = navigationBarInsets.bottom + (margins.bottom * densityFactor).toInt()
            )

            ViewCompat.onApplyWindowInsets(v, insets)
        }
    }

    private fun updateBaseMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        val view = getView()
        val layoutParams = view.layoutParams as? MarginLayoutParams ?: return

        layoutParams.apply {
            bottomMargin = bottom + defaultTopAndBottomMargin
            topMargin = top + defaultTopAndBottomMargin
            leftMargin = left
            rightMargin = right
        }
        view.layoutParams = layoutParams
    }

    private fun setGravity() {
        val view = getView()
        val layoutParams = view.layoutParams
        when (layoutParams) {
            is CoordinatorLayout.LayoutParams -> layoutParams.gravity = airyGravity
            is FrameLayout.LayoutParams -> layoutParams.gravity = airyGravity
        }
        view.layoutParams = layoutParams
    }

    private fun setContentPaddings(padding: SizeAttribute.Padding) {
        val densityFactor = if (padding.unit == SizeUnit.DP) {
            content.context.resources.displayMetrics.density
        } else {
            1f
        }

        val leftPadding = (padding.left * densityFactor).takeIf { it > 0f } ?: content.paddingLeft
        val rightPadding =
            (padding.right * densityFactor).takeIf { it > 0f } ?: content.paddingRight
        val topPadding = (padding.top * densityFactor).takeIf { it > 0f } ?: defaultVerticalPadding
        val bottomPadding =
            (padding.bottom * densityFactor).takeIf { it > 0f } ?: defaultVerticalPadding

        content.setPadding(
            leftPadding.toInt(),
            topPadding.toInt(),
            rightPadding.toInt(),
            bottomPadding.toInt()
        )
    }

    companion object {

        @SuppressWarnings("LongMethod")
        fun make(
            source: AirySnackBarSource,
            type: AirySnackBarType,
            attributes: List<AirySnackBarAttribute>
        ): AirySnackBar {

            val view = when (source) {
                is AirySnackBarSource.FromActivity -> source.activity.window?.decorView
                is AirySnackBarSource.FromDialog -> source.dialog.window?.decorView
                is AirySnackBarSource.FromView -> source.view
            }
                ?: throw IllegalArgumentException("${AirySnackBar::class.java.simpleName} source view is null")

            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException("Could not find a parent view for ${AirySnackBar::class.java.simpleName}.")

            (parent.layoutParams as? LinearLayout.LayoutParams)?.apply {
                gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            }

            val snackBarView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_airy_snackbar,
                parent,
                false
            ) as AirySnackBarView

            val airySnackBarModel = AirySnackBarModel()

            snackBarView.apply {
                val params = layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                orientation = LinearLayout.HORIZONTAL
                setSnackBarType(type)


                attributes.forEach { attr ->
                    when (attr) {
                        is TextAttribute.Text -> setMessage(attr.text)
                        is TextAttribute.TextColor -> setTextColor(
                            textColor = attr.textColor,
                            forceTextColor = attr.forceTextColor
                        )

                        is TextAttribute.TextSize -> setTextSize(size = attr.size)
                        is IconAttribute.NoIcon -> setIconVisibility(isVisible = false)
                        is IconAttribute.Icon -> setIcon(iconRes = attr.iconRes)
                        is IconAttribute.IconTint -> setIconColor(
                            iconTint = attr.iconTint,
                            forceTintColor = attr.forceTintColor
                        )

                        is RadiusAttribute.Radius -> setRoundPercent(attr.radius)
                        is AirySnackBarLayoutAttribute -> {
                            airySnackBarModel.snackBarLayoutAttribute.add(attr)
                            if (attr is SizeAttribute.Margin && source is AirySnackBarSource.FromView) {
                                (layoutParams as FrameLayout.LayoutParams).apply {
                                    leftMargin = attr.left
                                    topMargin = attr.top
                                    rightMargin = attr.right
                                    bottomMargin = attr.bottom
                                }
                            }
                        }
                    }
                }
            }

            return AirySnackBar(
                parent,
                snackBarView,
                airySnackBarModel.apply { this.view = view }
            )
        }
    }
}
