package com.santosystem.financial.balancing.dtos.response

import java.math.BigDecimal

class GoalResponseDTO(
    val id: Long,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal,
)
