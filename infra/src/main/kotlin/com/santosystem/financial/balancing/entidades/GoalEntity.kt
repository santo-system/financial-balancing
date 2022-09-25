package com.santosystem.financial.balancing.entidades

import com.santosystem.financial.balancing.models.Goal
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "goals")
class GoalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    val goalPercent: BigDecimal,

    val currentPercent: BigDecimal,
) {

    companion object {
        fun Goal.update(): GoalEntity {
            return GoalEntity(
                id = id,
                name = name,
                goalPercent = goalPercent,
                currentPercent = currentPercent,
            )
        }

        fun List<GoalEntity>.toModelList(): List<Goal> {
            return map {
                Goal(
                    id = it.id,
                    name = it.name,
                    goalPercent = it.goalPercent,
                    currentPercent = it.currentPercent
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
        )
    }
}
