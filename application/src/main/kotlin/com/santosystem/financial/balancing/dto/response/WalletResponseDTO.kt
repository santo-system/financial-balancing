package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Wallet

data class WalletResponseDTO(
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

        fun List<Wallet>.toResponseDTO(): List<WalletResponseDTO> {
            return map {
                it.toResponseDTO()
            }
        }
    }
}
