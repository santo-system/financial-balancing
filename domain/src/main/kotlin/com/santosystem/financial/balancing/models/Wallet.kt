package com.santosystem.financial.balancing.models

class Wallet(
    val id: Long,
    val name: String,
    val description: String,
    val main: Boolean,
    val goals: List<Goal>
)
