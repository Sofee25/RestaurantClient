package com.restaurantclient.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eightbitlab.com.blurview.RenderScriptBlur
import com.restaurantclient.R
import com.restaurantclient.data.dto.OrderResponse
import com.restaurantclient.databinding.ItemAdminOrderBinding
import com.restaurantclient.ui.common.setupGlassEffect

class OrderManagementAdapter(
    private val onStatusChange: (OrderResponse, String) -> Unit
) : ListAdapter<OrderResponse, OrderManagementAdapter.OrderViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class OrderViewHolder(
        private val binding: ItemAdminOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val statusMap = mapOf(
            binding.chipPending.id to "Pending",
            binding.chipProcessing.id to "Processing",
            binding.chipCompleted.id to "Completed",
            binding.chipCancelled.id to "Cancelled"
        )

        init {
            setupBlur()
        }

        private fun setupBlur() {
            val context = binding.root.context
            val whiteOverlay = ContextCompat.getColor(context, R.color.white_glass_overlay)
            binding.orderCardBlur.setOverlayColor(whiteOverlay)
            binding.orderCardBlur.setupGlassEffect(20f)
        }

        fun bind(order: OrderResponse) {
            binding.orderIdText.text = "Order #${order.order_id}"
            binding.orderAmountText.text = "${order.total_amount}"
            binding.orderMetaText.text = buildMetaText(order)

            binding.statusChipGroup.setOnCheckedStateChangeListener(null)
            val normalizedStatus = order.status?.lowercase() ?: "pending"
            statusMap.forEach { (chipId, statusValue) ->
                val chip = binding.statusChipGroup.findViewById<com.google.android.material.chip.Chip>(chipId)
                chip.isChecked = statusValue.lowercase() == normalizedStatus
            }

            binding.statusChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                val selectedId = checkedIds.firstOrNull() ?: return@setOnCheckedStateChangeListener
                val newStatus = statusMap[selectedId] ?: return@setOnCheckedStateChangeListener
                if (!newStatus.equals(order.status, ignoreCase = true)) {
                    onStatusChange(order, newStatus)
                }
            }
        }

        private fun buildMetaText(order: OrderResponse): String {
            val date = order.created_at?.substringBefore("T") ?: ""
            return "User #${order.user_id} • Placed $date"
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<OrderResponse>() {
        override fun areItemsTheSame(oldItem: OrderResponse, newItem: OrderResponse): Boolean =
            oldItem.order_id == newItem.order_id

        override fun areContentsTheSame(oldItem: OrderResponse, newItem: OrderResponse): Boolean =
            oldItem == newItem
    }
}
