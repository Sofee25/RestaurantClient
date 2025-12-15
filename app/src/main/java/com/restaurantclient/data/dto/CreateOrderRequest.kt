package com.restaurantclient.data.dto

data class CreateOrderRequest(
    val products: List<OrderItemRequest>
)
