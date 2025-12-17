package com.restaurantclient.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.restaurantclient.R
import com.restaurantclient.databinding.DialogGlassConfirmBinding
import com.restaurantclient.databinding.DialogGlassInputBinding

/**
 * Helper class for creating glassmorphic dialogs
 */
object GlassDialogHelper {

    /**
     * Create a glass confirmation dialog
     */
    fun showConfirmDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: String = "OK",
        negativeText: String = "Cancel",
        onPositive: () -> Unit,
        onNegative: (() -> Unit)? = null
    ): AlertDialog {
        val binding = DialogGlassConfirmBinding.inflate(LayoutInflater.from(context))
        
        // Setup blur effect
        val whiteOverlay = ContextCompat.getColor(context, R.color.white_glass_overlay)
        binding.dialogBlurView.setOverlayColor(whiteOverlay)
        binding.dialogBlurView.setupGlassEffect(25f)
        
        // Set content
        binding.dialogTitle.text = title
        binding.dialogMessage.text = message
        binding.dialogPositiveButton.text = positiveText
        binding.dialogNegativeButton.text = negativeText
        
        val dialog = AlertDialog.Builder(context, R.style.GlassDialogTheme)
            .setView(binding.root)
            .create()
        
        binding.dialogPositiveButton.setOnClickListener {
            onPositive()
            dialog.dismiss()
        }
        
        binding.dialogNegativeButton.setOnClickListener {
            onNegative?.invoke()
            dialog.dismiss()
        }
        
        dialog.show()
        return dialog
    }

    /**
     * Create a glass input dialog
     */
    fun showInputDialog(
        context: Context,
        title: String,
        hint: String,
        initialValue: String = "",
        positiveText: String = "OK",
        negativeText: String = "Cancel",
        onInput: (String) -> Unit,
        onNegative: (() -> Unit)? = null
    ): AlertDialog {
        val binding = DialogGlassInputBinding.inflate(LayoutInflater.from(context))
        
        // Setup blur effect
        val whiteOverlay = ContextCompat.getColor(context, R.color.white_glass_overlay)
        binding.dialogBlurView.setOverlayColor(whiteOverlay)
        binding.dialogBlurView.setupGlassEffect(25f)
        
        // Set content
        binding.dialogTitle.text = title
        binding.dialogInputLayout.hint = hint
        binding.dialogInputEditText.setText(initialValue)
        binding.dialogPositiveButton.text = positiveText
        binding.dialogNegativeButton.text = negativeText
        
        val dialog = AlertDialog.Builder(context, R.style.GlassDialogTheme)
            .setView(binding.root)
            .create()
        
        binding.dialogPositiveButton.setOnClickListener {
            val input = binding.dialogInputEditText.text.toString()
            onInput(input)
            dialog.dismiss()
        }
        
        binding.dialogNegativeButton.setOnClickListener {
            onNegative?.invoke()
            dialog.dismiss()
        }
        
        dialog.show()
        return dialog
    }

    /**
     * Create a simple glass info dialog
     */
    fun showInfoDialog(
        context: Context,
        title: String,
        message: String,
        buttonText: String = "OK",
        onDismiss: (() -> Unit)? = null
    ): AlertDialog {
        return showConfirmDialog(
            context = context,
            title = title,
            message = message,
            positiveText = buttonText,
            negativeText = "",
            onPositive = { onDismiss?.invoke() },
            onNegative = null
        ).apply {
            // Hide negative button for info dialog
            val binding = DialogGlassConfirmBinding.bind(findViewById<View>(R.id.dialog_blur_view)!!)
            binding.dialogNegativeButton.visibility = View.GONE
        }
    }
}

/**
 * Extension function for Context to show glass confirm dialog
 */
fun Context.showGlassConfirmDialog(
    title: String,
    message: String,
    positiveText: String = "OK",
    negativeText: String = "Cancel",
    onPositive: () -> Unit,
    onNegative: (() -> Unit)? = null
) = GlassDialogHelper.showConfirmDialog(
    this, title, message, positiveText, negativeText, onPositive, onNegative
)

/**
 * Extension function for Context to show glass input dialog
 */
fun Context.showGlassInputDialog(
    title: String,
    hint: String,
    initialValue: String = "",
    positiveText: String = "OK",
    negativeText: String = "Cancel",
    onInput: (String) -> Unit,
    onNegative: (() -> Unit)? = null
) = GlassDialogHelper.showInputDialog(
    this, title, hint, initialValue, positiveText, negativeText, onInput, onNegative
)

/**
 * Extension function for Context to show glass info dialog
 */
fun Context.showGlassInfoDialog(
    title: String,
    message: String,
    buttonText: String = "OK",
    onDismiss: (() -> Unit)? = null
) = GlassDialogHelper.showInfoDialog(this, title, message, buttonText, onDismiss)
