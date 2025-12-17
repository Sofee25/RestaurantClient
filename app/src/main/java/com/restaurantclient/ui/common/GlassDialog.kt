package com.restaurantclient.ui.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.annotation.LayoutRes
import com.restaurantclient.R

/**
 * Base class for dialogs with glassmorphism effect
 * Provides consistent glass styling for all dialogs
 */
abstract class GlassDialog(
    context: Context,
    @LayoutRes private val layoutResId: Int
) : Dialog(context, R.style.GlassDialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        
        setContentView(layoutResId)
        setupContent()
    }

    /**
     * Override this to setup dialog content
     */
    abstract fun setupContent()
}

/**
 * Simple glass confirmation dialog
 */
class GlassConfirmDialog(
    context: Context,
    private val title: String,
    private val message: String,
    private val positiveText: String = "OK",
    private val negativeText: String = "Cancel",
    private val onPositive: () -> Unit,
    private val onNegative: (() -> Unit)? = null
) : Dialog(context, R.style.GlassDialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        
        // Use Material AlertDialog with glass theme
        // Content will be setup through builder pattern
    }
}
