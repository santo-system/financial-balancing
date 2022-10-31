package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.GoalRequestDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.AssetService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/goals")
@ResponseBody
class GoalController(
    private val serviceGoal: GoalService,
    private val serviceWallet: WalletService,
    private val serviceAsset: AssetService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Operation(
        summary = "Deve criar uma meta",
        description = "Cria uma nova meta"
    )
    @PostMapping()
    fun createGoal(
        @RequestParam(name = "walletId", required = true) walletId: Long,
        @Valid @RequestBody request: GoalRequestDTO
    ): ResponseEntity<GoalResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to create a goal: {} with the walletId: {}", request, walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val createdGoal = serviceGoal.save(request.toDomain(walletId = it))

            logger.info("[$methodName] - Goal created: {} ", createdGoal)

            return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal.toResponseDTO())
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Operation(
        summary = "Deve alterar uma meta",
        description = "Altera uma meta pelo seu ID"
    )
    @PutMapping("/{goalId}")
    fun updateGoal(
        @PathVariable("goalId") goalId: Long,
        @Valid @RequestBody request: GoalRequestDTO
    ): ResponseEntity<GoalResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to update a goal: {} with the goalId: {}", request, goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val updatedGoal = serviceGoal.update(request.toDomain(goalId = it))

            logger.info("[$methodName] - Goal updated: {} ", updatedGoal)

            return ResponseEntity.ok(updatedGoal.toResponseDTO())
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @Operation(
        summary = "Deve retornar todas as metas",
        description = "Retorna uma lista com todas as metas"
    )
    @GetMapping()
    fun findAllGoals(): ResponseEntity<List<GoalResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all goals")

        val allGoals = serviceGoal.findAll()

        logger.info("[$methodName] - All goals found: {} ", allGoals)

        return ResponseEntity.ok(allGoals.toResponseDTO())
    }

    @Operation(
        summary = "Deve retornar todas os ativos de uma meta",
        description = "Retorna uma lista com todos os ativos de uma meta específica"
    )
    @GetMapping("/{goalId}/assets")
    fun findAllAssets(@PathVariable("goalId") goalId: Long): ResponseEntity<List<AssetResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all assets with the goalId: {}", goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val allAssets = serviceAsset.findAllByGoal(it)

            logger.info("[$methodName] - All assets found: {} ", allAssets)

            return ResponseEntity.ok(allAssets.toResponseDTO())
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @Operation(
        summary = "Deve retornar uma meta",
        description = "Retorna uma meta pelo seu ID"
    )
    @GetMapping("/{goalId}")
    fun findAGoal(@PathVariable("goalId") goalId: Long): ResponseEntity<GoalResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find a goal with the goalId: {}", goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val foundGoal = serviceGoal.findById(it)

            logger.info("[$methodName] - Goal found: {} ", foundGoal)

            return ResponseEntity.ok(foundGoal.toResponseDTO())
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @Operation(
        summary = "Deve deletar uma meta",
        description = "Deleta uma meta pelo seu ID"
    )
    @DeleteMapping("/{goalId}")
    fun deleteGoal(@PathVariable("goalId") goalId: Long): ResponseEntity<String> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to delete a goal with the goalId: {}", goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            serviceGoal.delete(it)

            logger.info("[$methodName] - Goal deleted")

            return ResponseEntity.ok().build()
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @Throws(ResponseStatusException::class)
    private fun goalNotFoundException(goalId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Goal not found with id: {} ", goalId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found")
    }

    @Throws(ResponseStatusException::class)
    private fun walletNotFoundException(walletId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Wallet not found with id: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found")
    }

}
