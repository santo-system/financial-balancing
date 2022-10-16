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

    override fun findAllByWallet(walletId: Long?): List<Goal> {
        verifyIdIsNull(walletId)
        return repository.findAllByWallet(walletId ?: 0)
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun findById(goalId: Long?): Goal {
        verifyIdIsNull(goalId)

        return when (val goalFound = repository.findById(goalId ?: 0)) {
            null -> throw businessNotFoundException(goalId)

            else -> goalFound
        }
    }

    override fun save(goal: Goal): Goal {
        verifyWalletIdIsNull(goal.walletId)
        return repository.save(goal)
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun update(goal: Goal): Goal {
        verifyIdIsNull(goal.id)

        return when (repository.findById(goal.id ?: 0)) {
            null -> throw businessNotFoundException(goal.id)

            else -> repository.save(goal)
        }
    }

    override fun delete(goalId: Long?) {
        verifyIdIsNull(goalId)
        repository.delete(goalId ?: 0)
    }

    override fun existsById(goalId: Long?): Boolean {
        return repository.existsById(goalId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyIdIsNull(goalId: Long?) {
        if (Objects.isNull(goalId) || goalId == 0L) {
            logger.error("Goal ID required.")
            BusinessError.required("Goal ID")
        }
    }

    @Throws(BusinessException::class)
    private fun verifyWalletIdIsNull(walletId: Long?) {
        if (Objects.isNull(walletId) || walletId == 0L) {
            logger.error("Wallet ID required.")
            BusinessError.required("Wallet ID")
        }
    }

    private fun businessNotFoundException(goalId: Long?): BusinessNotFoundException {
        logger.error("Goal not found with goalId: {} ", goalId)
        throw BusinessNotFoundException("Goal not found.")
    }

}
