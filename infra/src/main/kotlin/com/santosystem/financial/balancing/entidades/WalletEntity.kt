package com.santosystem.financial.balancing.entidades

import com.santosystem.financial.balancing.entidades.GoalEntity.Companion.toModelList
import com.santosystem.financial.balancing.entidades.GoalEntity.Companion.update
import com.santosystem.financial.balancing.models.Wallet
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "wallets")
class WalletEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    val description: String,

    val main: Boolean,

    val goalEntities: List<GoalEntity>
) {

    companion object {
        fun Wallet.update(): WalletEntity {
            return WalletEntity(
                id = id,
                name = name,
                description = description,
                main = main,
                goalEntities = goals.map { it.update() }
            )
        }
    }

    fun toModel(): Wallet {
        return Wallet(
            id = id,
            name = name,
            description = description,
            main = main,
            goals = goalEntities.toModelList()
        )
    }
}
