package com.santosystem.financial.balancing.controllers

import com.santosystem.financial.balancing.dtos.request.WalletRequestDTO
import com.santosystem.financial.balancing.dtos.response.WalletResponseDTO
import com.santosystem.financial.balancing.dtos.response.WalletResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.ports.repositories.WalletRepositoryPort
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
    private lateinit var walletRepositoryPort: WalletRepositoryPort

    @PostMapping
    fun createWallet(
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        logger.info("Init to create the wallet: {} ", request)

        val wallet = walletRepositoryPort.save(request.toDomain())

        val response = wallet.toResponseDTO()

        logger.info("Init to create the wallet: {} ", request)

        return ResponseEntity.ok(response)
    }

}
