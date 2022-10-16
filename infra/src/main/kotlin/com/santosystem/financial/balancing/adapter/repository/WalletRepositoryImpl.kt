package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.WalletJpaRepository
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toModel
import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import com.santosystem.financial.balancing.model.Wallet
import com.santosystem.financial.balancing.port.repository.GoalRepository
import com.santosystem.financial.balancing.port.repository.WalletRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WalletRepositoryImpl(
    private val repositoryWallet: WalletJpaRepository,
    private val repositoryGoal: GoalRepository
) : WalletRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findAll(): List<Wallet> {
        runCatching {
            return repositoryWallet.findAll().toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(walletId: Long): Wallet? {
        runCatching {
            return repositoryWallet.findByIdOrNull(walletId)?.toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(wallet: Wallet): Wallet {
        runCatching {
            return repositoryWallet.save(wallet.toEntity()).toModel()
        }.getOrElse {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(walletId: Long) {
        runCatching {
            repositoryGoal.deleteAll(
                repositoryGoal.findAllByWallet(walletId)
            )
            repositoryWallet.deleteById(walletId)
        }.onFailure {
            val methodName = object {}.javaClass.enclosingMethod.name
            throw infraUnexpectedException(it.message.toString(), methodName)
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun existsById(walletId: Long): Boolean {
        runCatching {
            return repositoryWallet.existsById(walletId)
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
