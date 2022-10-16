package com.santosystem.financial.balancing.adapter.repository.jpa

import com.santosystem.financial.balancing.entity.AssetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AssetJpaRepository : JpaRepository<AssetEntity, Long> {
//    fun findAllByGoalId(goalId: Long): List<AssetEntity>
    fun findByTicker(ticker: String): AssetEntity?
}
