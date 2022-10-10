package com.santosystem.financial.balancing.port.repository

import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Asset

interface AssetRepository {

    @Throws(InfraUnexpectedException::class)
    fun findAll(): List<Asset>

    @Throws(InfraUnexpectedException::class)
    fun findAllByGoal(goalId: Long): List<Asset>

    @Throws(InfraUnexpectedException::class)
    fun findById(assetId: Long): Asset?

    @Throws(InfraUnexpectedException::class)
    fun findByTicker(ticker: String): Asset?

    @Throws(InfraUnexpectedException::class)
    fun save(asset: Asset): Asset

    @Throws(InfraUnexpectedException::class)
    fun delete(assetId: Long)

}
