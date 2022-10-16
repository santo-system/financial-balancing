package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.GoalEntity.Companion.toModel
import com.santosystem.financial.balancing.model.Wallet
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
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

@EntityListeners(AuditingEntityListener::class)
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    val goalEntities: List<GoalEntity>? = emptyList(),

    @CreatedDate
    @Column(updatable = false)
    var createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null

) {

    companion object {
        fun Wallet.toEntity(): WalletEntity {
            return WalletEntity(
                id = id,
                name = name,
                description = description,
                main = main,
                goalEntities = goals?.toEntity(),
                createdAt,
                updatedAt
            )
        }

        fun List<WalletEntity>.toModel(): List<Wallet> {
            return map {
                it.toModel()
            }
        }
    }

    fun toModel(): Wallet {
        return Wallet(
            id = id,
            name = name,
            description = description,
            main = main,
            goals = goalEntities?.toModel(),
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
