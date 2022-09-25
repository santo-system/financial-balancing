package com.santosystem.financial.balancing.models

import java.time.ZonedDateTime

class Wallet(
    val id: Long,
    val name: String,
    val description: String,
    val main: Boolean,
    val goals: List<Goal>? = emptyList(),
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
