package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.model.Goal
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table

@Entity
@Table(name = "goals")
class GoalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(length = 100)
    val name: String,

    val goalPercent: BigDecimal,

    val currentPercent: BigDecimal,

    var createdAt: ZonedDateTime,

    var updatedAt: ZonedDateTime

) {

    companion object {
        fun Goal.toEntity(): GoalEntity {
            return GoalEntity(
                id = id,
                name = name,
                goalPercent = goalPercent,
                currentPercent = currentPercent,
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
