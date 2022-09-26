package com.santosystem.financial.balancing.ports.repositories

import com.santosystem.financial.balancing.models.Wallet

interface WalletRepositoryPort {

    fun findAll(): List<Wallet>

    fun findById(): List<Wallet>

    fun save(wallet: Wallet): Wallet

}
