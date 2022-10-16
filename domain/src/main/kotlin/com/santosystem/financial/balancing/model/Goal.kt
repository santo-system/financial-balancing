package com.santosystem.financial.balancing.model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class Goal(
    val id: Long? = null,
    val name: String,
    val goalPercent: BigDecimal,
    val currentPercent: BigDecimal? = BigDecimal.ZERO,
    val assets: List<Asset>? = emptyList(),
    val walletId: Long? = null,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
)
