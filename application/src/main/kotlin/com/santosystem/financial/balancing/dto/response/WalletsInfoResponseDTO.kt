package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.model.Wallet
import java.math.BigDecimal

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
                currentPercent = it.currentPercent ?: BigDecimal.ZERO
            )
        }
    }
}
