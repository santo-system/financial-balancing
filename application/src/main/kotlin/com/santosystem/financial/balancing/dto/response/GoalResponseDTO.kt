package com.santosystem.financial.balancing.dto.response

import java.math.BigDecimal

class GoalResponseDTO(
    val id: Long,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal,
)
