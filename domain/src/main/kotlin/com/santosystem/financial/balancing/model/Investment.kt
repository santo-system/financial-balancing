package com.santosystem.financial.balancing.model

import java.math.BigDecimal

data class Investment(
    val goal: Goal,
    val value: BigDecimal
)
