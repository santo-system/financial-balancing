package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.exception.BusinessError
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.WalletRepository
import com.santosystem.financial.balancing.port.service.WalletService
import org.slf4j.LoggerFactory
import java.util.Objects

class WalletServiceImpl(private val repositoryWallet: WalletRepository) : WalletService {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAll(): List<Wallet> {
        return repositoryWallet.findAll()
    }

    @Throws(BusinessNotFoundException::class)
    override fun findById(walletId: Long): Wallet {
        return when (val foundWallet = repositoryWallet.findById(walletId)) {
            null -> throw businessNotFoundException(walletId)
            else -> foundWallet
        }
    }

    override fun save(wallet: Wallet): Wallet {
        return repositoryWallet.save(wallet)
    }

    override fun update(wallet: Wallet): Wallet {
        return repositoryWallet.save(wallet)
    }

    override fun delete(walletId: Long) {
        repositoryWallet.delete(walletId)
    }

    @Throws(BusinessException::class)
    override fun existsById(walletId: Long?): Boolean {
        verifyIdIsNull(walletId)
        return repositoryWallet.existsById(walletId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyIdIsNull(walletId: Long?) {
        if (Objects.isNull(walletId) || walletId == 0L) {
            val methodName = object{}.javaClass.enclosingMethod.name
            logger.error("[$methodName] - Wallet ID required")
            BusinessError.required("Wallet ID")
        }
    }

    @Throws(BusinessNotFoundException::class)
    private fun businessNotFoundException(walletId: Long?): BusinessNotFoundException {
        val methodName = object{}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Wallet not found with id: {} ", walletId)
        throw BusinessNotFoundException("Wallet not found")
    }

}
