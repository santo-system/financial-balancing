package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.model.Investment
import com.santosystem.financial.balancing.port.service.InvestmentService
import java.math.BigDecimal

class InvestmentServiceImpl : InvestmentService {
    override fun calculate(valueInvested: BigDecimal, goals: List<Goal>): List<Investment> {
        return goals.map {
            Investment(it, BigDecimal.ONE)
        }
    }
}
