package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.dto.response.GoalResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.model.Wallet

data class WalletsInfoResponseDTO(
    val mainWalletId: Long,
    val wallets: List<WalletResponseDTO>
) {

    fun List<Wallet>.toWalletsInfoDTO(): WalletsInfoResponseDTO {
        return WalletsInfoResponseDTO(
            mainWalletId = firstOrNull { it.main }?.id ?: 0,
            map {
                WalletResponseDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    goals = it.goals?.toResponseDTO() ?: emptyList()
                )
            }
        )
    }
}
