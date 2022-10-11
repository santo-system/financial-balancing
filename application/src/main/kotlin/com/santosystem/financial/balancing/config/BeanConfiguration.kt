package com.santosystem.financial.balancing.config

import com.santosystem.financial.balancing.adapter.service.AssetServiceImpl
import com.santosystem.financial.balancing.adapter.service.GoalServiceImpl
import com.santosystem.financial.balancing.adapter.service.WalletServiceImpl
import com.santosystem.financial.balancing.port.repository.AssetRepository
import com.santosystem.financial.balancing.port.repository.GoalRepository
import com.santosystem.financial.balancing.port.repository.WalletRepository
import com.santosystem.financial.balancing.port.service.AssetService
import com.santosystem.financial.balancing.port.service.GoalService
import com.santosystem.financial.balancing.port.service.WalletService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun walletService(repository: WalletRepository): WalletService {
        return WalletServiceImpl(repository)
    }

    @Bean
    fun goalService(repository: GoalRepository): GoalService {
        return GoalServiceImpl(repository)
    }

    @Bean
    fun assetService(repository: AssetRepository): AssetService {
        return AssetServiceImpl(repository)
    }

}
