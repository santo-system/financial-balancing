package com.santosystem.financial.balancing.dtos

import java.math.BigDecimal

class GoalDTO(
    val id: Long,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal,
)
