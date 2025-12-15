package com.restaurantclient.data.dto

data class CreateUserRequest(
    val username: String,
    val password: String,
    val role: String // "Admin" or "Customer"
)
