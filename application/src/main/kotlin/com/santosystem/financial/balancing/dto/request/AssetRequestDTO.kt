package com.santosystem.financial.balancing.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import com.santosystem.financial.balancing.model.enums.Sector
import com.santosystem.financial.balancing.model.enums.Segment
import com.santosystem.financial.balancing.model.enums.SubSector
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AssetRequestDTO(
    val ticker: String,
    private val quantity: Int,
    private val averagePrice: BigDecimal,
) {
    fun toDomain(
        assetId: Long? = null,
        goalId: Long? = null,
        segment: Segment? = null,
        sector: Sector? = null,
        subSector: SubSector? = null,
        marketPriceCurrent: BigDecimal? = null,
        marketPricePreviousClose: BigDecimal? = null,
        shortName: String? = null,
        longName: String? = null,
        assetType: AssetType? = null
    ): Asset {
        return Asset(
            id = assetId,
            ticker = ticker,
            quantity = quantity,
            averagePrice = averagePrice,
            goalId = goalId,
            segment = segment,
            sector = sector,
            subSector = subSector,
            marketPriceCurrent = marketPriceCurrent,
            marketPricePreviousClose = marketPricePreviousClose,
            shortName = shortName,
            longName = longName,
            assetType = assetType
        )
    }
}

