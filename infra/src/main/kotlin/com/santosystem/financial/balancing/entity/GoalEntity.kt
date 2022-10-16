package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toModel
import com.santosystem.financial.balancing.model.Goal
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
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

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "goals")
class GoalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(length = 100)
    val name: String,

    val goalPercent: BigDecimal,

    val currentPercent: BigDecimal,

    @JoinColumn(name = "goal_id", referencedColumnName = "id")
    @OneToMany(fetch = FetchType.EAGER)
    val assetEntities: List<AssetEntity>? = emptyList(),

    @Column(name = "wallet_id", updatable = false)
    val walletId: Long? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null

) {

    companion object {
        fun Goal.toEntity(): GoalEntity {
            return GoalEntity(
                id = id,
                name = name,
                goalPercent = goalPercent,
                currentPercent = currentPercent ?: BigDecimal.ZERO,
                assetEntities = assets?.map { it.toEntity() },
                walletId = walletId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }

        fun List<GoalEntity>.toModel(): List<Goal> {
            return map {
                it.toModel()
            }
        }
    }

    fun toModel(): Goal {
        return Goal(
            id = id,
            name = name,
            goalPercent = goalPercent,
            currentPercent = currentPercent,
            assets = assetEntities?.toModel(),
            walletId = walletId,
            createdAt = createdAt,
            updatedAt = updatedAt
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
