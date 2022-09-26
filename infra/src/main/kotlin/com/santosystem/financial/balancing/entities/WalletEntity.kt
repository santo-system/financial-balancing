package com.santosystem.financial.balancing.entities

import com.santosystem.financial.balancing.entities.GoalEntity.Companion.toModelList
import com.santosystem.financial.balancing.entities.GoalEntity.Companion.updateFromDomain
import com.santosystem.financial.balancing.models.Wallet
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table


@Entity
@Table(name = "wallets")
data class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @Column(length = 100)
    val name: String,

    @Column(length = 255)
    val description: String,

    val main: Boolean,

    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.EAGER)
    val goalEntities: List<GoalEntity>? = emptyList(),

    var createdAt: ZonedDateTime? = null,

    var updatedAt: ZonedDateTime? = null

) {

    companion object {
        fun Wallet.updateFromDomain(): WalletEntity {
            return WalletEntity(
                id = id,
                name = name,
                description = description,
                main = main,
                goalEntities = goals?.map { it.updateFromDomain() },
                createdAt,
                updatedAt
            )
        }
    }

    fun toModel(): Wallet {
        return Wallet(
            id = id,
            name = name,
            description = description,
            main = main,
            goals = goalEntities?.toModelList(),
            createdAt,
            updatedAt
        )
    }

    @PrePersist
    private fun onCreate() {
        createdAt = ZonedDateTime.now()
        updatedAt = ZonedDateTime.now()
    }

    @PreUpdate
    private fun onUpdate() {
        updatedAt = ZonedDateTime.now()
    }
}
