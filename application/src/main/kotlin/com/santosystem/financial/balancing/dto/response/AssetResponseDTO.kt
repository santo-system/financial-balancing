package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import com.santosystem.financial.balancing.model.enums.Sector
import com.santosystem.financial.balancing.model.enums.Segment
import com.santosystem.financial.balancing.model.enums.SubSector
import java.math.BigDecimal

data class AssetResponseDTO(
    val id: Long?,
    val ticker: String,
    val shortName: String,
    val longName: String,
    val segment: Segment? = null,
    val sector: Sector? = null,
    val subSector: SubSector? = null,
    val assetType: AssetType? = null,
    val averagePrice: BigDecimal,
    val marketPriceCurrent: BigDecimal? = null,
    val marketPricePreviousClose: BigDecimal? = null,
    val quantity: Int,
    val goalId: Long?,
) {
    companion object {
        fun Asset.toResponseDTO(): AssetResponseDTO {
            return AssetResponseDTO(
                id = id,
                ticker = ticker,
                shortName = shortName.orEmpty(),
                longName = longName.orEmpty(),
                segment = segment,
                sector = sector,
                subSector = subSector,
                assetType = assetType,
                averagePrice = averagePrice,
                marketPriceCurrent = marketPriceCurrent,
                marketPricePreviousClose = marketPricePreviousClose,
                quantity = quantity,
                goalId = goalId
            )
        }

        fun List<Asset>.toResponseDTO(): List<AssetResponseDTO> {
            return map {
                it.toResponseDTO()
            }
        }
    }
}
