package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Wallet

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
