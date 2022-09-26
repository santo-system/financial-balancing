package com.santosystem.financial.balancing.dtos.response

import com.santosystem.financial.balancing.models.Wallet

class WalletResponseDTO(
    val id: Long?,
    val name: String,
    val description: String,
    val goals: List<GoalResponseDTO>
) {
    companion object {
        fun Wallet.toResponseDTO(): WalletResponseDTO {
            return WalletResponseDTO(
                id = id,
                name = name,
                description = description,
                goals = emptyList(),
            )
        }
    }
}
