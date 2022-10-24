package com.santosystem.financial.balancing.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class YahooFinanceQuoteResponse(
    val quoteResponse: QuoteResponse
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class QuoteResponse(
        val result: List<Result>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Result(
        val shortName: String,
        val longName: String,
        val regularMarketPrice: BigDecimal,
        val regularMarketPreviousClose: BigDecimal
    )
}
