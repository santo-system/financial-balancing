package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.WalletRequestDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/wallets")
class WalletController() {

    private val logger = LoggerFactory.getLogger(WalletController::class.java)

    @Autowired
    private lateinit var service: WalletService

    @PostMapping
    fun createWallet(
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        logger.info("Starting to create the wallet: {} ", request)

        val wallet = service.save(request.toDomain())

        val response = wallet.toResponseDTO()

        logger.info("Init to create the wallet: {} ", request)

        return ResponseEntity.ok(response)
    }

}
