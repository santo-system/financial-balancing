package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.GoalJpaRepository
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toModel
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.port.repository.AssetRepository
import com.santosystem.financial.balancing.port.repository.GoalRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class GoalRepositoryImpl(
    private val repositoryGoal: GoalJpaRepository,
    private val repositoryAsset: AssetRepository
) : GoalRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Goal> {
        runCatching {
            return repositoryGoal.findAll().toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAllByWallet(walletId: Long): List<Goal> {
        runCatching {
            return repositoryGoal.findAllByWalletId(walletId).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(goalId: Long): Goal? {
        runCatching {
            return repositoryGoal.findByIdOrNull(goalId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(goal: Goal): Goal {
        runCatching {
            return repositoryGoal.save(goal.toEntity()).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(goalId: Long) {
        runCatching {
            repositoryAsset.deleteAll(
                repositoryAsset.findAllByGoal(goalId)
            )
            repositoryGoal.deleteById(goalId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun deleteAll(goals: List<Goal>) {
        runCatching {
            repositoryAsset.deleteAll(
                repositoryAsset.findAllByGoal(goals.firstOrNull()?.id ?: 0L)
            )
            repositoryGoal.deleteAll(goals.toEntity())
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun existsById(goalId: Long): Boolean {
        runCatching {
            return repositoryGoal.existsById(goalId)
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
