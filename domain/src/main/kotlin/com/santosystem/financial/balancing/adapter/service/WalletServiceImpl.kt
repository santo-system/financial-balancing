package com.santosystem.financial.balancing.adapter.service

import com.santosystem.financial.balancing.exception.BusinessError
import com.santosystem.financial.balancing.exception.BusinessException
import com.santosystem.financial.balancing.exception.BusinessNotFoundException
import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.WalletRepository
import com.santosystem.financial.balancing.port.service.WalletService
import org.slf4j.LoggerFactory
import java.util.Objects

class WalletServiceImpl(private val repository: WalletRepository) : WalletService {

    private val logger = LoggerFactory.getLogger(WalletServiceImpl::class.java)

    override fun findAll(): List<Wallet> {
        return repository.findAll()
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun findById(walletId: Long?): Wallet {
        verifyWalletIdIsNull(walletId)

        val wallet = repository.findById(walletId ?: 0)

        return when (wallet) {
            null -> {
                logger.error("Wallet not found with walletId: {} ", walletId)
                throw BusinessNotFoundException("Wallet not found.")
            }

            else -> wallet
        }
    }

    override fun save(wallet: Wallet): Wallet {
        return repository.save(wallet)
    }

    @Throws(BusinessNotFoundException::class, BusinessException::class)
    override fun update(wallet: Wallet): Wallet {
        verifyWalletIdIsNull(wallet.id)
        return repository.save(wallet)
    }

    override fun delete(walletId: Long?) {
        verifyWalletIdIsNull(walletId)
        repository.delete(walletId ?: 0)
    }

    @Throws(BusinessException::class)
    private fun verifyWalletIdIsNull(walletId: Long?) {
        if (Objects.isNull(walletId) || walletId == 0L) {
            logger.error("Wallet ID required")
            BusinessError.required("Wallet ID")
        }
    }

}