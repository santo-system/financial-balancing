package com.santosystem.financial.balancing.dtos.response

import com.santosystem.financial.balancing.models.Goal
import com.santosystem.financial.balancing.models.Wallet

class WalletsInfoResponseDTO(
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
                    goals = it.goals?.toGoalDTO() ?: emptyList()
                )
            }
        )
    }

    private fun List<Goal>.toGoalDTO(): List<GoalResponseDTO> {
        return map {
            GoalResponseDTO(
                id = it.id,
                name = it.name,
                goalPercent = it.goalPercent,
                currentPercent = it.currentPercent
            )
        }
    }
}
