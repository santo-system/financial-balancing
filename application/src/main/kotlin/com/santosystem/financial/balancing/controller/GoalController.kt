package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.GoalRequestDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.port.service.GoalService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("goals")
@ResponseBody
class GoalController(
    private val serviceGoal: GoalService,
    private val serviceWallet: WalletService
) {

    private val logger = LoggerFactory.getLogger(GoalController::class.java)

    @PostMapping()
    fun createGoal(
        @RequestParam(name = "walletId", required = true) walletId: Long,
        @Valid @RequestBody request: GoalRequestDTO
    ): ResponseEntity<GoalResponseDTO> {
        logger.info("Starting to create a goal: {} with the walletId: {}", request, walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val createdGoal = serviceGoal.save(request.toDomain(walletId = it))

            logger.info("Goal created: {} ", createdGoal)

            return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal.toResponseDTO())
        }

        throw walletNotFoundException(walletId)
    }

    @PutMapping("{goalId}")
    fun updateGoal(
        @PathVariable("goalId") goalId: Long,
        @Valid @RequestBody request: GoalRequestDTO
    ): ResponseEntity<GoalResponseDTO> {
        logger.info("Starting to update a goal: {} with the goalId: {}", request, goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val updatedGoal = serviceGoal.update(request.toDomain(goalId = it))

            logger.info("Goal updated: {} ", updatedGoal)

            return ResponseEntity.ok(updatedGoal.toResponseDTO())
        }

        throw goalNotFoundException(goalId)
    }

    @GetMapping()
    fun findAllGoals(): ResponseEntity<List<GoalResponseDTO>> {
        logger.info("Starting to find all goals")

        val allGoals = serviceGoal.findAll()

        logger.info("All goals found: {} ", allGoals)

        return ResponseEntity.ok(allGoals.toResponseDTO())
    }

    @GetMapping("{goalId}")
    fun findAGoal(@PathVariable("goalId") goalId: Long): ResponseEntity<GoalResponseDTO> {
        logger.info("Starting to find a goal with the goalId: {}", goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val foundGoal = serviceGoal.findById(it)

            logger.info("Goal found: {} ", foundGoal)

            return ResponseEntity.ok(foundGoal.toResponseDTO())
        }

        throw goalNotFoundException(goalId)
    }

    @GetMapping()
    fun findAllGoalsByWallet(
        @RequestParam(name = "walletId", required = true) walletId: Long
    ): ResponseEntity<List<GoalResponseDTO>> {
        logger.info("Starting to find all goals with the walletId: {}", walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val allGoals = serviceGoal.findAllByWallet(walletId)

            logger.info("All goals found: {} ", allGoals)

            return ResponseEntity.ok(allGoals.toResponseDTO())
        }

        throw walletNotFoundException(walletId)
    }

    @DeleteMapping("{goalId}")
    fun deleteGoal(@PathVariable("goalId") goalId: Long): ResponseEntity<Unit> {
        logger.info("Starting to delete a goal with the goalId: {}", goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            serviceGoal.delete(goalId)

            logger.info("Goal deleted")

            return ResponseEntity.ok().build()
        }

        throw goalNotFoundException(goalId)
    }

    @Throws(ResponseStatusException::class)
    private fun goalNotFoundException(goalId: Long?): BusinessNotFoundException {
        logger.error("Goal not found with id: {} ", goalId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found with id $goalId")
    }

    @Throws(ResponseStatusException::class)
    private fun walletNotFoundException(walletId: Long?): BusinessNotFoundException {
        logger.error("Wallet not found with id: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found with id $walletId")
    }

}
