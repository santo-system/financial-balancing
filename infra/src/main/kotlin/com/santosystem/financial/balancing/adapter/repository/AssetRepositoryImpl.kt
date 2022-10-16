package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.AssetJpaRepository
import com.santosystem.financial.balancing.entity.AssetEntity
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toModel
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.port.repository.AssetRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AssetRepositoryImpl(private val repository: AssetJpaRepository) : AssetRepository {

    private val logger = LoggerFactory.getLogger(AssetRepositoryImpl::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Asset> {
        runCatching {
            val entityList: List<AssetEntity> = repository.findAll()
            return entityList.toModel()
        }.getOrElse {
            logger.error("Asset unexpected error in findAll method. Original message: {}", it.message)
            throw InfraUnexpectedException("Asset unexpected error in findAll method")
        }
    }

//    override fun findAllByGoal(goalId: Long): List<Asset> {
//        runCatching {
//            val entityList: List<AssetEntity> = repository.findAllByGoalId(goalId)
//            return entityList.toModelList()
//        }.getOrElse {
//            logger.error("Asset unexpected error in findAll method. Original message: {}", it.message)
//            throw InfraUnexpectedException("Asset unexpected error in findAll method")
//        }
//    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(assetId: Long): Asset? {
        runCatching {
            val entity: AssetEntity? = repository.findByIdOrNull(assetId)
            return entity?.toModel()
        }.getOrElse {
            logger.error("Asset unexpected error in findById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Asset unexpected error in findById method")
        }
    }

    override fun findByTicker(ticker: String): Asset? {
        runCatching {
            val entity: AssetEntity? = repository.findByTicker(ticker)
            return entity?.toModel()
        }.getOrElse {
            logger.error("Asset unexpected error in findById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Asset unexpected error in findById method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(asset: Asset): Asset {
        runCatching {
            val entity: AssetEntity = repository.save(asset.toEntity())
            return entity.toModel()
        }.getOrElse {
            logger.error("Asset unexpected error in save method. Original message: {}", it.message)
            throw InfraUnexpectedException("Asset unexpected error in save method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(assetId: Long) {
        runCatching {
            repository.deleteById(assetId)
        }.getOrElse {
            logger.error("Asset unexpected error in delete method. Original message: {}", it.message)
            throw InfraUnexpectedException("Asset unexpected error in delete method")
        }
    }

}
