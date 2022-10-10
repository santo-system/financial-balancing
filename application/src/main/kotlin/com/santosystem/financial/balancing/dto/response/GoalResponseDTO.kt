package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal

data class GoalResponseDTO(
    val id: Long?,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal? = BigDecimal.ZERO,
) {
    companion object {
        fun Goal.toResponseDTO(): GoalResponseDTO {
            return GoalResponseDTO(
                id = id,
                name = name,
                goalPercent = goalPercent,
                currentPercent = currentPercent,
            )
        }
    }
}
