package com.santosystem.financial.balancing.dto.response

import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import java.math.BigDecimal

data class AssetResponseDTO(
    val id: Long?,
    val ticker: String,
    val name: String? = null,
    val segment: String? = null,
    val sector: String? = null,
    val subSector: String? = null,
    val assetType: AssetType? = null,
    val averagePrice: BigDecimal,
    val quantity: Int,
    val goalId: Long?,
) {
    companion object {
        fun Asset.toResponseDTO(): AssetResponseDTO {
            return AssetResponseDTO(
                id = id,
                ticker = ticker,
                name = name.orEmpty(),
                segment = segment,
                sector = sector,
                subSector = subSector,
                assetType = assetType,
                averagePrice = averagePrice,
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
