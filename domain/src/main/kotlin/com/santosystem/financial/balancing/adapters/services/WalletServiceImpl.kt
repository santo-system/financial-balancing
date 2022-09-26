package com.santosystem.financial.balancing.adapters.services

import com.santosystem.financial.balancing.models.Wallet
import com.santosystem.financial.balancing.ports.services.WalletServicePort
import com.santosystem.financial.balancing.exceptions.BusinessNotFoundException

class WalletServiceImpl : WalletServicePort {
    override fun findAll(): List<Wallet> {
        TODO("Not yet implemented")
    }

    override fun save(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    @Throws(BusinessNotFoundException::class)
    override fun update(wallet: Wallet) {
        TODO("Not yet implemented")
    }
}
