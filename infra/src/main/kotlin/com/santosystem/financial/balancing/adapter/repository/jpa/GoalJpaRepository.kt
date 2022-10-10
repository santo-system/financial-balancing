package com.santosystem.financial.balancing.adapter.repository.jpa

import com.santosystem.financial.balancing.entity.GoalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoalJpaRepository : JpaRepository<GoalEntity, Long> {
    fun findAllByWalletId(walletId: Long): List<GoalEntity>
}
