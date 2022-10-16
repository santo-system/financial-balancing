package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.WalletRequestDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.port.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/wallets")
@ResponseBody
class WalletController(
    private val service: WalletService
) {

    private val logger = LoggerFactory.getLogger(WalletController::class.java)

    @PostMapping
    fun createWallet(
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to create a wallet: {} ", request)

        val createdWallet = service.save(request.toDomain())

        logger.info("[$methodName] - Wallet created: {} ", createdWallet)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet.toResponseDTO())
    }

    @PutMapping("/{walletId}")
    fun updateWallet(
        @PathVariable("walletId") walletId: Long,
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to update a wallet: {} with the walletId: {}", request, walletId)

        walletId.takeIf {
            service.existsById(it)
        }?.also {
            val updatedWallet = service.update(request.toDomain())

            logger.info("[$methodName] - Wallet updated: {} ", updatedWallet)

            return ResponseEntity.ok(updatedWallet.toResponseDTO())
        }

        throw walletNotFoundException(walletId)
    }

    @GetMapping()
    fun findAllWallets(): ResponseEntity<List<WalletResponseDTO>> {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all wallets")

        val allWallets = service.findAll()

        logger.info("[$methodName] - All wallets found: {} ", allWallets)

        return ResponseEntity.ok(allWallets.toResponseDTO())
    }

    @GetMapping("/{walletId}")
    fun findAWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<WalletResponseDTO> {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find a wallet with the walletId: {}", walletId)

        walletId.takeIf {
            service.existsById(it)
        }?.also {
            val foundWallet = service.findById(it)

            logger.info("[$methodName] - Wallet found: {} ", foundWallet)

            return ResponseEntity.ok(foundWallet.toResponseDTO())
        }

        throw walletNotFoundException(walletId)
    }

    @DeleteMapping("/{walletId}")
    fun deleteWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<Unit> {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to delete a wallet with the walletId: {}", walletId)

        walletId.takeIf {
            service.existsById(it)
        }?.also {
            service.delete(it)

            logger.info("[$methodName] - Wallet deleted")

            return ResponseEntity.ok().build()
        }

        throw walletNotFoundException(walletId)
    }

    @Throws(ResponseStatusException::class)
    private fun walletNotFoundException(walletId: Long?): BusinessNotFoundException {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Wallet not found with id: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found")
    }

}
