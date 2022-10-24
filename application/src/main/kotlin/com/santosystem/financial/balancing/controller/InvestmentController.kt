package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.response.InvestGoalResponseDTO
import com.santosystem.financial.balancing.dto.response.InvestGoalResponseDTO.Companion.toInvestGoalResponseDTO
import com.santosystem.financial.balancing.port.service.GoalService
import com.santosystem.financial.balancing.port.service.InvestmentService
import com.santosystem.financial.balancing.port.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/investments")
@ResponseBody
class InvestmentController(
    private val serviceInvestment: InvestmentService,
    private val serviceWallet: WalletService,
    private val serviceGoal: GoalService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{walletId}")
    fun calculate(@PathVariable("walletId") walletId: Long): ResponseEntity<List<InvestGoalResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all goals with the walletId: {}", walletId)

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val allGoals = serviceGoal.findAllByWallet(it)

            logger.info("[$methodName] - All goals found: {} ", allGoals)

            return ResponseEntity.ok(allGoals.toInvestGoalResponseDTO())
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
