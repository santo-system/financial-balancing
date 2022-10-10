package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.GoalJpaRepository
import com.santosystem.financial.balancing.entity.GoalEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toModelList
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
            val entityList: List<GoalEntity> = repository.findAll()
            return entityList.toModelList()
        }.getOrElse {
            logger.error("Goal unexpected error in findAll method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findAll method")
        }
    }

    override fun findAllByWallet(walletId: Long): List<Goal> {
        runCatching {
            val entityList: List<GoalEntity> = repository.findAllByWalletId(walletId)
            return entityList.toModelList()
        }.getOrElse {
            logger.error("Goal unexpected error in findAllByWallet method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findAllByWallet method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(goalId: Long): Goal? {
        runCatching {
            val entity: GoalEntity? = repository.findByIdOrNull(goalId)
            return entity?.toModel()
        }.getOrElse {
            logger.error("Goal unexpected error in findById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Goal unexpected error in findById method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(goal: Goal): Goal {
        runCatching {
            val entity: GoalEntity = repository.save(goal.toEntity())
            return entity.toModel()
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

}
