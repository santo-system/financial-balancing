package com.santosystem.financial.balancing.model

import com.santosystem.financial.balancing.model.enums.AssetType
import com.santosystem.financial.balancing.model.enums.Sector
import com.santosystem.financial.balancing.model.enums.Segment
import com.santosystem.financial.balancing.model.enums.SubSector
import java.math.BigDecimal
import java.time.ZonedDateTime

data class Asset(
    val id: Long? = null,
    val ticker: String,
    val shortName: String? = null,
    val longName: String? = null,
    val segment: Segment? = null,
    val sector: Sector? = null,
    val subSector: SubSector? = null,
    val assetType: AssetType? = null,
    val averagePrice: BigDecimal,
    val marketPriceCurrent: BigDecimal? = null,
    val marketPricePreviousClose: BigDecimal? = null,
    val quantity: Int,
    val goalId: Long? = null,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
)
