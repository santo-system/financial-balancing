package com.santosystem.financial.balancing.port.repository

import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Wallet

interface WalletRepository {

    @Throws(InfraUnexpectedException::class)
    fun findAll(): List<Wallet>

    @Throws(InfraUnexpectedException::class)
    fun findById(walletId: Long): Wallet?

    @Throws(InfraUnexpectedException::class)
    fun save(wallet: Wallet): Wallet

    @Throws(InfraUnexpectedException::class)
    fun delete(walletId: Long)

}
