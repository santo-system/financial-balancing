package com.santosystem.financial.balancing.adapter.repository

import com.santosystem.financial.balancing.adapter.repository.jpa.WalletJpaRepository
import com.santosystem.financial.balancing.entity.WalletEntity
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.WalletEntity.Companion.toModelList
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
            val entityList: List<WalletEntity> = repository.findAll()
            return entityList.toModelList()
        }.getOrElse {
            logger.error("Wallet unexpected error in findAll method. Original message: {}", it.message)
            throw InfraUnexpectedException("Wallet unexpected error in findAll method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional(readOnly = true)
    override fun findById(walletId: Long): Wallet? {
        runCatching {
            val entity: WalletEntity? = repository.findByIdOrNull(walletId)
            return entity?.toModel()
        }.getOrElse {
            logger.error("Wallet unexpected error in findById method. Original message: {}", it.message)
            throw InfraUnexpectedException("Wallet unexpected error in findById method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun save(wallet: Wallet): Wallet {
        runCatching {
            val entity: WalletEntity = repository.save(wallet.toEntity())
            return entity.toModel()
        }.getOrElse {
            logger.error("Wallet unexpected error in save method. Original message: {}", it.message)
            throw InfraUnexpectedException("Wallet unexpected error in save method")
        }
    }

    @Throws(InfraUnexpectedException::class)
    @Transactional
    override fun delete(walletId: Long) {
        runCatching {
            repository.deleteById(walletId)
        }.getOrElse {
            logger.error("Wallet unexpected error in delete method. Original message: {}", it.message)
            throw InfraUnexpectedException("Wallet unexpected error in delete method")
        }
    }

}
