package com.santosystem.financial.balancing.port.repository

import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Goal

interface GoalRepository {

    @Throws(InfraUnexpectedException::class)
    fun findAll(): List<Goal>

    @Throws(InfraUnexpectedException::class)
    fun findAllByWallet(walletId: Long): List<Goal>

    @Throws(InfraUnexpectedException::class)
    fun findById(goalId: Long): Goal?

    @Throws(InfraUnexpectedException::class)
    fun save(goal: Goal): Goal

    @Throws(InfraUnexpectedException::class)
    fun delete(goalId: Long)

}
