package com.santosystem.financial.balancing.dtos

import com.santosystem.financial.balancing.models.Goal
import com.santosystem.financial.balancing.models.Wallet

class WalletsInfoDTO(
    val mainWalletId: Long,
    val wallets: List<WalletDTO>
) {

    fun List<Wallet>.toWalletsInfoDTO(): WalletsInfoDTO {
        return WalletsInfoDTO(
            mainWalletId = first { it.main }.id,
            map {
                WalletDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    goals = it.goals?.toGoalDTO() ?: emptyList()
                )
            }
        )
    }

    private fun List<Goal>.toGoalDTO(): List<GoalDTO> {
        return map {
            GoalDTO(
                id = it.id,
                name = it.name,
                goalPercent = it.goalPercent,
                currentPercent = it.currentPercent
            )
        }
    }
}
