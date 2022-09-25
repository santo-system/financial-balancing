package com.santosystem.financial.balancing.models

import java.math.BigDecimal

class Goal(
    val id: Long,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal,
)
