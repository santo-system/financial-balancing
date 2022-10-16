package com.santosystem.financial.balancing.port.service

import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Wallet

interface WalletService {

    fun findAll(): List<Wallet>

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun findById(walletId: Long?): Wallet

    fun save(wallet: Wallet): Wallet

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    fun update(wallet: Wallet): Wallet

    fun delete(walletId: Long?)

    fun existsById(goalId: Long?): Boolean

}
