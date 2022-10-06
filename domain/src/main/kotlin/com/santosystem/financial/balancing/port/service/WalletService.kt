package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Wallet

interface WalletService {

    fun findAll(): List<Wallet>

    @Throws(BusinessNotFoundException::class)
    fun findById(walletId: Long?): Wallet

    fun save(wallet: Wallet): Wallet

    @Throws(BusinessNotFoundException::class)
    fun update(wallet: Wallet): Wallet

    fun delete(walletId: Long?)

}
