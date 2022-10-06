package com.santosystem.financial.balancing.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.santosystem.financial.balancing.model.Wallet
import javax.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class WalletRequestDTO(
    @NotBlank
    val name: String,
    val description: String?,
) {
    fun toDomain(): Wallet {
        return Wallet(
            name = name,
            description = description.orEmpty(),
            main = false,
        )
    }
}

