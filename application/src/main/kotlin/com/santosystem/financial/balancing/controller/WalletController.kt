package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.WalletRequestDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO
import com.santosystem.financial.balancing.dto.response.WalletResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.GoalService
import com.santosystem.financial.balancing.port.service.WalletService
import io.swagger.v3.oas.annotations.Operation
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
    private val serviceWallet: WalletService,
    private val serviceGoal: GoalService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Operation(
        summary = "Deve criar uma carteira",
        description = "Cria uma nova carteira"
    )
    @PostMapping
    fun createWallet(
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to create a wallet: {} ", request)

        val createdWallet = serviceWallet.save(request.toDomain())

        logger.info("[$methodName] - Wallet created: {} ", createdWallet)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdWallet.toResponseDTO())
    }

    @Operation(
        summary = "Deve alterar uma carteira",
        description = "Altera uma carteira pelo seu ID"
    )
    @PutMapping("/{walletId}")
    fun updateWallet(
        @PathVariable("walletId") walletId: Long,
        @Valid @RequestBody request: WalletRequestDTO
    ): ResponseEntity<WalletResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to update a wallet: {} with the walletId: {}", request, walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val updatedWallet = serviceWallet.update(request.toDomain())

            logger.info("[$methodName] - Wallet updated: {} ", updatedWallet)

            return ResponseEntity.ok(updatedWallet.toResponseDTO())
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Operation(
        summary = "Deve retornar todas as carteiras",
        description = "Retorna uma lista com todas as carteiras"
    )
    @GetMapping()
    fun findAllWallets(): ResponseEntity<List<WalletResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all wallets")

        val allWallets = serviceWallet.findAll()

        logger.info("[$methodName] - All wallets found: {} ", allWallets)

        return ResponseEntity.ok(allWallets.toResponseDTO())
    }

    @Operation(
        summary = "Deve retornar todas as metas de uma carteira",
        description = "Retorna uma lista com todas as metas de uma carteira específica"
    )
    @GetMapping("/{walletId}/goals")
    fun findAllGoals(@PathVariable("walletId") walletId: Long): ResponseEntity<List<GoalResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all goals with the walletId: {}", walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val allGoals = serviceGoal.findAllByWallet(it)

            logger.info("[$methodName] - All goals found: {} ", allGoals)

            return ResponseEntity.ok(allGoals.toResponseDTO())
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Operation(
        summary = "Deve retornar uma carteira",
        description = "Retorna uma carteira pelo seu ID"
    )
    @GetMapping("/{walletId}")
    fun findAWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<WalletResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find a wallet with the walletId: {}", walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val foundWallet = serviceWallet.findById(it)

            logger.info("[$methodName] - Wallet found: {} ", foundWallet)

            return ResponseEntity.ok(foundWallet.toResponseDTO())
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Operation(
        summary = "Deve deletar uma carteira",
        description = "Deleta uma carteira pelo seu ID"
    )
    @DeleteMapping("/{walletId}")
    fun deleteWallet(@PathVariable("walletId") walletId: Long): ResponseEntity<String> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to delete a wallet with the walletId: {}", walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            serviceWallet.delete(it)

            logger.info("[$methodName] - Wallet deleted")

            return ResponseEntity.ok("OK")
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Throws(ResponseStatusException::class)
    private fun walletNotFoundException(walletId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Wallet not found with id: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found")
    }

}
