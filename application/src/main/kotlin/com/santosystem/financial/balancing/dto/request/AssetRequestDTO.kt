package com.santosystem.financial.balancing.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.santosystem.financial.balancing.model.Asset
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AssetRequestDTO(
    @NotBlank
    private val ticker: String,
    @Positive
    private val quantity: Int,
    @Positive
    private val averagePrice: BigDecimal,
) {
    fun toDomain(): Asset {
        return Asset(
            ticker = ticker,
            quantity = quantity,
            averagePrice = averagePrice
        )
    }
}

