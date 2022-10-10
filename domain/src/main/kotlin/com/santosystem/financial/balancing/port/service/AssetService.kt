package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Asset

interface AssetService {

    fun findAll(): List<Asset>

    fun findAllByGoal(goalId: Long): List<Asset>

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun findById(assetId: Long?): Asset

    @Throws(BusinessNotFoundException::class)
    fun findByTicker(ticker: String): Asset

    fun save(asset: Asset): Asset

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun update(asset: Asset): Asset

    fun delete(assetId: Long?)

}
