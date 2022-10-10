package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.exception.BusinessError
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.port.repository.AssetRepository
import com.santosystem.financial.balancing.port.service.AssetService
import org.slf4j.LoggerFactory
import java.util.Objects

class AssetServiceImpl(private val repository: AssetRepository) : AssetService {

    private val logger = LoggerFactory.getLogger(AssetServiceImpl::class.java)

    override fun findAll(): List<Asset> {
        return repository.findAll()
    }

    override fun findAllByGoal(goalId: Long): List<Asset> {
        verifyIdIsNull(goalId)
        return repository.findAllByGoal(goalId)
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun findById(assetId: Long?): Asset {
        verifyIdIsNull(assetId)

        return when (val assetFound = repository.findById(assetId ?: 0)) {
            null -> throw businessNotFoundException(assetId)

            else -> assetFound
        }
    }

    @Throws(BusinessNotFoundException::class)
    override fun findByTicker(ticker: String): Asset {
        return when (val assetFound = repository.findByTicker(ticker)) {
            null -> throw businessNotFoundException(assetId = null, ticker)

            else -> assetFound
        }
    }

    override fun save(asset: Asset): Asset {
        return repository.save(asset)
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun update(asset: Asset): Asset {
        verifyIdIsNull(asset.id)

        return when (repository.findById(asset.id ?: 0)) {
            null -> throw businessNotFoundException(asset.id)

            else -> repository.save(asset)
        }
    }

    override fun delete(assetId: Long?) {
        verifyIdIsNull(assetId)
        repository.delete(assetId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyIdIsNull(id: Long?) {
        if (Objects.isNull(id) || id == 0L) {
            logger.error("Asset ID required.")
            BusinessError.required("Asset ID")
        }
    }

    private fun businessNotFoundException(assetId: Long?, ticker: String? = null): BusinessNotFoundException {
        when (ticker) {
            null -> {
                logger.error("Asset not found with assetId: {} ", assetId)
                throw BusinessNotFoundException("Asset not found.")
            }

            else -> {
                logger.error("Asset not found with ticker: {} ", ticker)
                throw BusinessNotFoundException("Asset not found.")
            }
        }
    }

}
