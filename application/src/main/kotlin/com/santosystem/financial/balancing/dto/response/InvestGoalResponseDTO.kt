package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal

data class InvestGoalResponseDTO(
    val id: Long?,
    val walletId: Long?,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal? = BigDecimal.ZERO,
    val investValue: BigDecimal,
) {
    companion object {
        fun Goal.toInvestGoalResponseDTO(): InvestGoalResponseDTO {
            return InvestGoalResponseDTO(
                id = id,
                walletId = walletId,
                name = name,
                goalPercent = goalPercent,
                currentPercent = currentPercent ?: BigDecimal.ZERO,
                investValue = BigDecimal.ONE
            )
        }

        fun List<Goal>.toInvestGoalResponseDTO(): List<InvestGoalResponseDTO> {
            return map {
                it.toInvestGoalResponseDTO()
            }
        }
    }
}
