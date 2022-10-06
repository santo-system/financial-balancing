package com.santosystem.financial.balancing.config

import com.santosystem.financial.balancing.adapter.service.WalletServiceImpl
import com.santosystem.financial.balancing.port.repository.WalletRepository
import com.santosystem.financial.balancing.port.service.WalletService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun walletService(repository: WalletRepository): WalletService {
        return WalletServiceImpl(repository)
    }
}
