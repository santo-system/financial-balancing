package com.santosystem.financial.balancing.dtos

class WalletDTO(
    val id: Long,
    val name: String,
    val description: String,
    val goals: List<GoalDTO>
)
