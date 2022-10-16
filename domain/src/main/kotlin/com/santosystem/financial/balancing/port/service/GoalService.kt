package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Goal

interface GoalService {

    fun findAll(): List<Goal>

    fun findAllByWallet(walletId: Long): List<Goal>

    @Throws(BusinessNotFoundException::class)
    fun findById(goalId: Long): Goal

    fun save(goal: Goal): Goal

    fun update(goal: Goal): Goal

    fun delete(goalId: Long)

    @Throws(BusinessException::class)
    fun existsById(goalId: Long?): Boolean

}
