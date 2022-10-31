package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.model.Investment
import java.math.BigDecimal

interface InvestmentService {

    fun calculate(valueInvested: BigDecimal, goals: List<Goal>): List<Investment>

}
