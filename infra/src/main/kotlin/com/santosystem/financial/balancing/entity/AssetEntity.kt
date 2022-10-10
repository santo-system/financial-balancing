package com.santosystem.financial.balancing.entity

import com.santosystem.financial.balancing.model.Asset
import com.santosystem.financial.balancing.model.enums.AssetType
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.NotBlank

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

    var createdAt: ZonedDateTime? = null,

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
                createdAt,
                updatedAt
            )
        }

        fun List<AssetEntity>.toModelList(): List<Asset> {
            return map {
                Asset(
                    id = it.id,
                    ticker = it.ticker,
                    name = it.name,
                    segment = it.segment,
                    sector = it.sector,
                    subSector = it.subSector,
                    assetType = it.assetType,
                    averagePrice = it.averagePrice,
                    quantity = it.quantity,
                    it.createdAt,
                    it.updatedAt
                )
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
