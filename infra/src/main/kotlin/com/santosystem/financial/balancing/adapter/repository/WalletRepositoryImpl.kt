package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.WalletJpaRepository
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toModel
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.WalletRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WalletRepositoryImpl(private val repository: WalletJpaRepository) : WalletRepository {

    private val logger = LoggerFactory.getLogger(WalletRepositoryImpl::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Wallet> {
        runCatching {
            return repository.findAll().toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(walletId: Long): Wallet? {
        runCatching {
            return repository.findByIdOrNull(walletId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(wallet: Wallet): Wallet {
        runCatching {
            return repository.save(wallet.toEntity()).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(walletId: Long) {
        runCatching {
            repository.deleteById(walletId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    override fun existsById(walletId: Long): Boolean {
        runCatching {
            return repository.existsById(walletId)
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    private fun infraUnexpectedException(message: String, method: String): InfraUnexpectedException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error(
            "[$methodName] - An unexpected error occurred with the Wallet in the $method method." +
                    " Original message: {}", message
        )
        throw InfraUnexpectedException("An unexpected error occurred with the Wallet")
    }

}
