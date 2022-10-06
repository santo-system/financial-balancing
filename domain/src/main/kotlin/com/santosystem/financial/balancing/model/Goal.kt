package com.santosystem.financial.balancing.model

import java.math.BigDecimal
import java.time.ZonedDateTime

class Goal(
    val id: Long,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
