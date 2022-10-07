package com.santosystem.financial.balancing.adapter.repository.jpa

import com.santosystem.financial.balancing.entity.WalletEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletJpaRepository : JpaRepository<WalletEntity, Long> {
}
