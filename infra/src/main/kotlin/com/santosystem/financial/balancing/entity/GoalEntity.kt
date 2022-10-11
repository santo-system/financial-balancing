package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toEntity
import com.santosystem.financial.balancing.entity.AssetEntity.Companion.toModelList
import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal
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

    var createdAt: ZonedDateTime? = null,

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
                createdAt,
                updatedAt
            )
        }

        fun List<GoalEntity>.toModelList(): List<Goal> {
            return map {
                Goal(
                    id = it.id,
                    name = it.name,
                    goalPercent = it.goalPercent,
                    currentPercent = it.currentPercent,
                    assets = it.assetEntities?.toModelList(),
                    it.createdAt,
                    it.updatedAt
                )
            }
        }
    }

    fun toModel(): Goal {
        return Goal(
            id = id,
            name = name,
            goalPercent = goalPercent,
            currentPercent = currentPercent,
            assets = assetEntities?.toModelList(),
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
