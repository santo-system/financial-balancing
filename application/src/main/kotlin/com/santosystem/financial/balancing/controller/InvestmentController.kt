package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.response.InvestmentResponseDTO
import com.santosystem.financial.balancing.dto.response.InvestmentResponseDTO.Companion.toInvestmentResponseDTO
import com.santosystem.financial.balancing.port.service.GoalService
import com.santosystem.financial.balancing.port.service.InvestmentService
import com.santosystem.financial.balancing.port.service.WalletService
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/investments")
@ResponseBody
class InvestmentController(
    private val serviceInvestment: InvestmentService,
    private val serviceWallet: WalletService,
    private val serviceGoal: GoalService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Operation(
        summary = "Deve retornar o cálculo do balanceamento financeiro",
        description = "Retorna o cálculo do balanceamento financeiro de uma carteira específica"
    )
    @GetMapping("/{walletId}")
    fun calculate(
        @Valid
        @Positive
        @RequestParam(name = "valueInvested", required = true) valueInvested: BigDecimal,
        @PathVariable("walletId") walletId: Long
    ): ResponseEntity<List<InvestmentResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info(
            "[$methodName] - Starting to calculate the financial balance with the walletId: {}" +
                    " and the valueInvested {} ", walletId, valueInvested
        )

        walletId.takeIf {
            serviceWallet.existsById(it)
        }?.also {
            val allGoals = serviceGoal.findAllByWallet(it)

            if (allGoals.isEmpty()) {
                throw goalNotFoundException(walletId)
            }

            logger.info("[$methodName] - All goals found: {} ", allGoals)

            val investments = serviceInvestment.calculate(valueInvested, allGoals)

            return ResponseEntity.ok(investments.toInvestmentResponseDTO())
        }

        throw walletNotFoundException(walletId = walletId)
    }

    @Throws(ResponseStatusException::class)
    private fun walletNotFoundException(walletId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Wallet not found with id: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found")
    }

    @Throws(ResponseStatusException::class)
    private fun goalNotFoundException(walletId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Goal not found with walletId: {} ", walletId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found")
    }

}
