package com.restaurantclient.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.restaurantclient.R

/**
 * Extension functions for creating glassmorphic Snackbars
 */
object GlassSnackbar {

    /**
     * Show a glass-styled Snackbar
     */
    fun make(
        view: View,
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT
    ): Snackbar {
        val snackbar = Snackbar.make(view, message, duration)
        
        // Style the snackbar with glass effect
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(
            ContextCompat.getColor(view.context, R.color.glass_overlay_light)
        )
        
        // Add elevation
        snackbarView.elevation = 8f
        
        // Round corners
        snackbarView.background = ContextCompat.getDrawable(
            view.context,
            R.drawable.glass_card_border
        )
        
        return snackbar
    }

    /**
     * Show success snackbar with glass effect
     */
    fun success(view: View, message: String): Snackbar {
        return make(view, message, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(view.context, R.color.admin_success))
            setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        }
    }

    /**
     * Show error snackbar with glass effect
     */
    fun error(view: View, message: String): Snackbar {
        return make(view, message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(ContextCompat.getColor(view.context, R.color.admin_error))
            setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        }
    }

    /**
     * Show warning snackbar with glass effect
     */
    fun warning(view: View, message: String): Snackbar {
        return make(view, message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(ContextCompat.getColor(view.context, R.color.admin_warning))
            setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        }
    }

    /**
     * Show info snackbar with glass effect
     */
    fun info(view: View, message: String): Snackbar {
        return make(view, message, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(view.context, R.color.admin_info))
            setTextColor(ContextCompat.getColor(view.context, android.R.color.white))
        }
    }
}

/**
 * Extension function for View to show glass snackbar
 */
fun View.showGlassSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    GlassSnackbar.make(this, message, duration).show()
}

/**
 * Extension function for View to show success glass snackbar
 */
fun View.showSuccessSnackbar(message: String) {
    GlassSnackbar.success(this, message).show()
}

/**
 * Extension function for View to show error glass snackbar
 */
fun View.showErrorSnackbar(message: String) {
    GlassSnackbar.error(this, message).show()
}

/**
 * Extension function for View to show warning glass snackbar
 */
fun View.showWarningSnackbar(message: String) {
    GlassSnackbar.warning(this, message).show()
}

/**
 * Extension function for View to show info glass snackbar
 */
fun View.showInfoSnackbar(message: String) {
    GlassSnackbar.info(this, message).show()
}
