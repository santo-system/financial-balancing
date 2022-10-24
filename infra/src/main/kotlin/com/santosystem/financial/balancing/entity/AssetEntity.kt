package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import com.santosystem.financial.balancing.model.enums.Sector
import com.santosystem.financial.balancing.model.enums.Segment
import com.santosystem.financial.balancing.model.enums.SubSector
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
import javax.validation.constraints.Positive

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

    @Column(name = "short_name", length = 255)
    val shortName: String,

    @Column(name = "long_name", length = 255)
    val longName: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    val segment: Segment?,

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    val sector: Sector?,

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_sector",length = 255)
    val subSector: SubSector?,

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    val assetType: AssetType?,

    @Positive
    @Column(name = "average_price")
    val averagePrice: BigDecimal,

    @Column(name = "market_price_current")
    val marketPriceCurrent: BigDecimal?,

    @Column(name = "market_price_previous_close")
    val marketPricePreviousClose: BigDecimal?,

    @Positive
    val quantity: Int,

    @Column(name = "goal_id", updatable = false)
    val goalId: Long? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    var createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null

) {

    companion object {
        fun Asset.toEntity(): AssetEntity {
            return AssetEntity(
                id = id,
                ticker = ticker,
                shortName = shortName.orEmpty(),
                longName = longName.orEmpty(),
                segment = segment,
                sector = sector,
                subSector = subSector,
                assetType = assetType,
                averagePrice = averagePrice,
                marketPriceCurrent = marketPriceCurrent,
                marketPricePreviousClose = marketPricePreviousClose,
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
            shortName = shortName,
            longName = longName,
            segment = segment,
            sector = sector,
            subSector = subSector,
            assetType = assetType,
            averagePrice = averagePrice,
            marketPriceCurrent = marketPriceCurrent,
            marketPricePreviousClose = marketPricePreviousClose,
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
