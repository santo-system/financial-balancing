package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.AssetJpaRepository
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toModel
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.Goal
import com.santosystem.financial.balancing.port.repository.AssetRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AssetRepositoryImpl(private val repository: AssetJpaRepository) : AssetRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Asset> {
        runCatching {
            return repository.findAll().toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAllByGoal(goalId: Long): List<Asset> {
        runCatching {
            return repository.findAllByGoalId(goalId).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(assetId: Long): Asset? {
        runCatching {
            return repository.findByIdOrNull(assetId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findByTicker(ticker: String, goalId: Long): Asset? {
        runCatching {
            return repository.findByTickerAndGoalId(ticker, goalId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(asset: Asset): Asset {
        runCatching {
            return repository.save(asset.toEntity()).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(assetId: Long) {
        runCatching {
            repository.deleteById(assetId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun deleteAll(assets: List<Asset>) {
        runCatching {
            repository.deleteAll(assets.toEntity())
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun existsById(assetId: Long): Boolean {
        runCatching {
            return repository.existsById(assetId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    private fun infraUnexpectedException(message: String, method: String): InfraUnexpectedException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error(
            "[$methodName] - An unexpected error occurred with the Asset in the $method method." +
                    " Original message: {}", message
        )
        throw InfraUnexpectedException("An unexpected error occurred with the Asset")
    }

}
