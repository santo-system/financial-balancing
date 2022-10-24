package com.santosystem.financial.balancing.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GoalRequestDTO(
    private val name: String,
    private val goalPercent: BigDecimal,
) {
    fun toDomain(goalId: Long? = null, walletId: Long? = null): Goal {
        return Goal(
            id = goalId,
            name = name,
            goalPercent = goalPercent,
            walletId = walletId
        )
    }
}

