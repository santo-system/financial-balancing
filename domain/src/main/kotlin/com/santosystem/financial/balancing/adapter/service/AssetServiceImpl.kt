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

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAll(): List<Asset> {
        return repository.findAll()
    }

    override fun findAllByGoal(goalId: Long): List<Asset> {
        return repository.findAllByGoal(goalId)
    }

    @Throws(BusinessNotFoundException::class)
    override fun findById(assetId: Long): Asset {
        return when (val assetFound = repository.findById(assetId)) {
            null -> throw businessNotFoundException(assetId = assetId)
            else -> assetFound
        }
    }

    @Throws(BusinessNotFoundException::class)
    override fun findByTicker(ticker: String, goalId: Long): Asset {
        return when (val assetFound = repository.findByTicker(ticker, goalId)) {
            null -> throw businessNotFoundException(ticker = ticker)
            else -> assetFound
        }
    }

    override fun save(asset: Asset): Asset {
        return repository.save(asset)
    }

    override fun update(asset: Asset): Asset {
        return repository.save(asset)
    }

    override fun delete(assetId: Long) {
        repository.delete(assetId)
    }

    @Throws(BusinessException::class)
    override fun existsById(goalId: Long?): Boolean {
        verifyIdIsNull(goalId)
        return repository.existsById(goalId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyIdIsNull(id: Long?) {
        if (Objects.isNull(id) || id == 0L) {
            val methodName = object {}.javaClass.enclosingMethod.name
            logger.error("[$methodName] - Asset ID required")
            BusinessError.required("Asset ID")
        }
    }

    @Throws(BusinessNotFoundException::class)
    private fun businessNotFoundException(assetId: Long? = null, ticker: String? = null): BusinessNotFoundException {
        val methodName = object {}.javaClass.enclosingMethod.name
        when (ticker) {
            null -> {
                logger.error("[$methodName] - Asset not found with assetId: {} ", assetId)
                throw BusinessNotFoundException("Asset not found")
            }

            else -> {
                logger.error("[$methodName] - Asset not found with ticker: {} ", ticker)
                throw BusinessNotFoundException("Asset not found")
            }
        }
    }

}
