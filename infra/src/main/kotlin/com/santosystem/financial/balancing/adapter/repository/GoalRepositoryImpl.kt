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
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    override fun findAllByWallet(walletId: Long): List<Goal> {
        runCatching {
            return repository.findAllByWalletId(walletId).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(goalId: Long): Goal? {
        runCatching {
            return repository.findByIdOrNull(goalId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(goal: Goal): Goal {
        runCatching {
            return repository.save(goal.toEntity()).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(goalId: Long) {
        runCatching {
            repository.deleteById(goalId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    override fun existsById(goalId: Long): Boolean {
        runCatching {
            return repository.existsById(goalId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    private fun infraUnexpectedException(message: String, method: String): InfraUnexpectedException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error(
            "[$methodName] - An unexpected error occurred with the Goal in the $method method." +
                    " Original message: {}", message
        )
        throw InfraUnexpectedException("An unexpected error occurred with the Goal")
    }

}
