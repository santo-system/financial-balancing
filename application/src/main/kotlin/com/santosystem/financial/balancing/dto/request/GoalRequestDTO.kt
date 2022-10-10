package com.santosystem.financial.balancing.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GoalRequestDTO(
    @NotBlank
    private val name: String,
    @Positive
    private val goalPercent: BigDecimal,
) {
    fun toDomain(): Goal {
        return Goal(
            name = name,
            goalPercent = goalPercent
        )
    }
}

