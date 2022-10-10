package com.santosystem.financial.balancing.model

import java.time.ZonedDateTime

data class Wallet(
    val id: Long? = null,
    val name: String,
    val description: String,
    val main: Boolean,
    val goals: List<Goal>? = emptyList(),
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
)
