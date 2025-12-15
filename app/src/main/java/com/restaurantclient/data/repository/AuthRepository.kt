package com.restaurantclient.data.repository

import com.restaurantclient.data.Result
import com.restaurantclient.data.dto.RoleDTO
import com.restaurantclient.data.dto.UserDTO
import com.restaurantclient.data.network.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCurrentUserInfo(): Result<UserDTO> {
        return try {
            val response = apiService.getCurrentUser()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("Failed to get current user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Fallback method to determine admin status
    suspend fun checkAdminStatus(): Result<Boolean> {
        return try {
            // Try to access admin endpoint - if successful, user is admin
            val response = apiService.getAllUsers()
            Result.Success(response.isSuccessful)
        } catch (e: Exception) {
            // If endpoint fails due to access denied, user is not admin
            Result.Success(false)
        }
    }
}
