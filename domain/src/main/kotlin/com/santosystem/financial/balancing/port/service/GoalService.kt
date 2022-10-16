package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Goal

interface GoalService {

    fun findAll(): List<Goal>

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun findAllByWallet(walletId: Long?): List<Goal>

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun findById(goalId: Long?): Goal

    fun save(goal: Goal): Goal

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun update(goal: Goal): Goal

    fun delete(goalId: Long?)

    fun existsById(goalId: Long?): Boolean

}
