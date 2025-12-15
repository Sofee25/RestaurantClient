package com.restaurantclient.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.restaurantclient.MainActivity
import com.restaurantclient.ui.auth.AuthViewModel

abstract class BaseAdminActivity : AppCompatActivity() {

    protected val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enforceAdminAccess()
    }

    override fun onResume() {
        super.onResume()
        enforceAdminAccess()
    }

    protected fun setupAdminToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean = false) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    }

    protected fun bindAdminOnlyView(view: View) {
        view.isVisible = authViewModel.isAdmin()
    }

    private fun enforceAdminAccess() {
        authViewModel.loadStoredUserInfo()
        if (!authViewModel.isAdmin()) {
            Toast.makeText(this, "Admin access required", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
