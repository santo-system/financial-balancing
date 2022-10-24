package com.santosystem.financial.balancing.util.reader

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CompanyUtil(
    val id: Long,
    @JsonProperty("nm_empresa")
    val name: String,
    @JsonProperty("setor_economico")
    val sector: String,
    @JsonProperty("subsetor")
    val subSector: String,
    @JsonProperty("segmento")
    val segment: String,
    @JsonProperty("cd_acao")
    val tickers: String,
) {

    fun tickersToList(tickers: String): List<String> = tickers.split(", ")

}
