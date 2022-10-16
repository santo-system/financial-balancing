package com.santosystem.financial.balancing.model

import com.santosystem.financial.balancing.model.enums.AssetType
import java.math.BigDecimal
import java.time.ZonedDateTime

data class Asset(
    val id: Long? = null,
    val ticker: String,
    val name: String? = null,
    val segment: String? = null,
    val sector: String? = null,
    val subSector: String? = null,
    val assetType: AssetType? = null,
    val averagePrice: BigDecimal,
    val quantity: Int,
    val goalId: Long? = null,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
)
