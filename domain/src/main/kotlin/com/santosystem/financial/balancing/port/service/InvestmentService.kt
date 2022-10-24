package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.model.Investment
import java.math.BigDecimal

interface InvestmentService {

    fun calculate(value: BigDecimal): Investment

}
