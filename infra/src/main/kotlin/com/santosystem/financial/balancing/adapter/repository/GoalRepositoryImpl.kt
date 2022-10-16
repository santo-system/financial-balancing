package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.GoalJpaRepository
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toModel
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.port.repository.GoalRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GoalRepositoryImpl(private val repository: GoalJpaRepository) : GoalRepository {

    private val logger = LoggerFactory.getLogger(GoalRepositoryImpl::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Goal> {
        runCatching {
            return repository.findAll().toModel()
        }.getOrElse {
            logger.error("Goal unexpected error in findAll method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findAll method")
        }
    }

    override fun findAllByWallet(walletId: Long): List<Goal> {
        runCatching {
            return repository.findAllByWalletId(walletId).toModel()
        }.getOrElse {
            logger.error("Goal unexpected error in findAllByWallet method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findAllByWallet method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(goalId: Long): Goal? {
        runCatching {
            return repository.findByIdOrNull(goalId)?.toModel()
        }.getOrElse {
            logger.error("Goal unexpected error in findById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findById method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(goal: Goal): Goal {
        runCatching {
            return repository.save(goal.toEntity()).toModel()
        }.getOrElse {
            logger.error("Goal unexpected error in save method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in save method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(goalId: Long) {
        runCatching {
            repository.deleteById(goalId)
        }.getOrElse {
            logger.error("Goal unexpected error in delete method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in delete method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    override fun existsById(goalId: Long): Boolean {
        runCatching {
            return repository.existsById(goalId)
        }.getOrElse {
            logger.error("Goal unexpected error in existsById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in existsById method")
        }
    }

}
