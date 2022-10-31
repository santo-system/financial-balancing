package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.model.Investment
import java.math.BigDecimal

data class InvestmentResponseDTO(
    val goal: Goal,
    val value: BigDecimal
) {
    companion object {
        fun Investment.toInvestmentResponseDTO(): InvestmentResponseDTO {
            return InvestmentResponseDTO(
                goal = goal,
                value = value,
            )
        }

        fun List<Investment>.toInvestmentResponseDTO(): List<InvestmentResponseDTO> {
            return map {
                it.toInvestmentResponseDTO()
            }
        }
    }
}
