package com.santosystem.financial.balancing.ports.services

import com.santosystem.financial.balancing.models.Wallet
import com.santosystem.financial.exceptions.BusinessNotFoundException

interface WalletServicePort {

    fun findAll(): List<Wallet>

    fun save(wallet: Wallet)

    @Throws(BusinessNotFoundException::class)
    fun update(wallet: Wallet)

}
