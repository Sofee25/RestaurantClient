package com.restaurantclient.data.dto

data class OrderItemResponse(
    val productId: Int,
    val quantity: Int,
    val price: Double
)
