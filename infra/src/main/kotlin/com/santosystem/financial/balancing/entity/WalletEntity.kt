package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toModelList
import com.santosystem.financial.balancing.model.Wallet
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
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "wallets")
data class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @NotBlank
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
        fun Wallet.toEntity(): WalletEntity {
            return WalletEntity(
                id = id,
                name = name,
                description = description,
                main = main,
                goalEntities = goals?.map { it.toEntity() },
                createdAt,
                updatedAt
            )
        }

        fun List<WalletEntity>.toModelList(): List<Wallet> {
            return map {
                Wallet(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    main = it.main,
                    goals = it.goalEntities?.toModelList(),
                    it.createdAt,
                    it.updatedAt
                )
            }
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
        updatedAt = createdAt
    }

    @PreUpdate
    private fun onUpdate() {
        updatedAt = ZonedDateTime.now()
    }
}
