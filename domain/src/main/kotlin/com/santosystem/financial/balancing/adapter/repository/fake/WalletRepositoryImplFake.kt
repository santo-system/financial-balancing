package com.santosystem.financial.balancing.adapter.repository.fake

import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.WalletRepository
import org.slf4j.LoggerFactory

class WalletRepositoryImplFake : WalletRepository {

    private val logger = LoggerFactory.getLogger(WalletRepositoryImplFake::class.java)

    private val walletList: MutableList<Wallet> = mutableListOf()

    override fun findAll(): List<Wallet> {
        return walletList.toList()
    }

    override fun findById(walletId: Long): Wallet? {
        return walletList.find { it.id == walletId }
    }

    override fun save(wallet: Wallet): Wallet {
        walletList.add(wallet)
        return walletList.last()
    }

    override fun delete(walletId: Long) {
        val wallet = walletList.find { it.id == walletId }
        walletList.remove(wallet)
    }

}
