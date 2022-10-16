package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "assets")
data class AssetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @NotBlank
    @Column(length = 100)
    val ticker: String,

    @Column(length = 255)
    val name: String?,

    @Column(length = 255)
    val segment: String?,

    @Column(length = 255)
    val sector: String?,

    @Column(length = 255)
    val subSector: String?,

    @Enumerated(EnumType.STRING)
    val assetType: AssetType?,

    val averagePrice: BigDecimal,

    val quantity: Int,

    @Column(name = "goal_id", updatable = false)
    val goalId: Long? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null

) {

    companion object {
        fun Asset.toEntity(): AssetEntity {
            return AssetEntity(
                id = id,
                ticker = ticker,
                name = name,
                segment = segment,
                sector = sector,
                subSector = subSector,
                assetType = assetType,
                averagePrice = averagePrice,
                quantity = quantity,
                goalId = goalId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }

        fun List<Asset>.toEntity(): List<AssetEntity> {
            return map {
                it.toEntity()
            }
        }

        fun List<AssetEntity>.toModel(): List<Asset> {
            return map {
                it.toModel()
            }
        }
    }

    fun toModel(): Asset {
        return Asset(
            id = id,
            ticker = ticker,
            name = name,
            segment = segment,
            sector = sector,
            subSector = subSector,
            assetType = assetType,
            averagePrice = averagePrice,
            quantity = quantity,
            goalId = goalId,
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
