package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.exception.BusinessError
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.port.repository.GoalRepository
import com.santosystem.financial.balancing.port.service.GoalService
import org.slf4j.LoggerFactory
import java.util.Objects

class GoalServiceImpl(private val repository: GoalRepository) : GoalService {

    private val logger = LoggerFactory.getLogger(GoalServiceImpl::class.java)

    override fun findAll(): List<Goal> {
        return repository.findAll()
    }

    override fun findAllByWallet(walletId: Long): List<Goal> {
        return repository.findAllByWallet(walletId)
    }

    @Throws(BusinessNotFoundException::class)
    override fun findById(goalId: Long): Goal {
        return when (val foundGoal = repository.findById(goalId)) {
            null -> throw businessNotFoundException(goalId)
            else -> foundGoal
        }
    }

    override fun save(goal: Goal): Goal {
        return repository.save(goal)
    }

    override fun update(goal: Goal): Goal {
        return repository.save(goal)
    }

    override fun delete(goalId: Long) {
        repository.delete(goalId)
    }

    @Throws(BusinessException::class)
    override fun existsById(goalId: Long?): Boolean {
        verifyIdIsNull(goalId)
        return repository.existsById(goalId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyIdIsNull(goalId: Long?) {
        if (Objects.isNull(goalId) || goalId == 0L) {
            val methodName = object{}.javaClass.enclosingMethod.name
            logger.error("[$methodName] - Goal ID required.")
            BusinessError.required("Goal ID")
        }
    }

    @Throws(BusinessNotFoundException::class)
    private fun businessNotFoundException(goalId: Long?): BusinessNotFoundException {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Goal not found with id: {} ", goalId)
        throw BusinessNotFoundException("Goal not found")
    }

}
