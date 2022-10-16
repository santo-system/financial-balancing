package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.AssetRequestDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.AssetService
import com.santosystem.financial.balancing.port.service.GoalService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/assets")
@ResponseBody
class AssetController(
    private val serviceAsset: AssetService,
    private val serviceGoal: GoalService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    fun createAsset(
        @RequestParam(name = "goalId", required = true) goalId: Long,
        @Valid @RequestBody request: AssetRequestDTO
    ): ResponseEntity<AssetResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to create an asset: {} with the goalId: {}", request, goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val createdAsset = serviceAsset.save(request.toDomain(goalId = goalId))

            logger.info("[$methodName] - Asset created: {} ", createdAsset)

            return ResponseEntity.status(HttpStatus.CREATED).body(createdAsset.toResponseDTO())
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @PutMapping("/{assetId}")
    fun updateAsset(
        @PathVariable("assetId") assetId: Long,
        @Valid @RequestBody request: AssetRequestDTO
    ): ResponseEntity<AssetResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to update an asset: {} with the assetId: {}", request, assetId)

        assetId.takeIf {
            serviceAsset.existsById(it)
        }?.also {
            val updatedAsset = serviceAsset.update(request.toDomain(assetId = assetId))

            logger.info("[$methodName] - Asset updated: {} ", updatedAsset)

            return ResponseEntity.ok(updatedAsset.toResponseDTO())
        }

        throw assetNotFoundException(assetId = assetId)
    }

    @GetMapping()
    fun findAllAssets(): ResponseEntity<List<AssetResponseDTO>> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find all assets")

        val allAssets = serviceAsset.findAll()

        logger.info("[$methodName] - All assets found: {} ", allAssets)

        return ResponseEntity.ok(allAssets.toResponseDTO())
    }

    @GetMapping("/{assetId}")
    fun findAnAsset(@PathVariable("assetId") assetId: Long): ResponseEntity<AssetResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find an asset with the assetId: {}", assetId)

        assetId.takeIf {
            serviceAsset.existsById(it)
        }?.also {
            val foundAsset = serviceAsset.findById(it)

            logger.info("[$methodName] - Asset found: {} ", foundAsset)

            return ResponseEntity.ok(foundAsset.toResponseDTO())
        }

        throw assetNotFoundException(assetId = assetId)
    }

    @GetMapping("/{ticker}/{goalId}")
    fun findAnAssetByTicker(
        @PathVariable("ticker") ticker: String,
        @PathVariable("goalId") goalId: Long
    ): ResponseEntity<AssetResponseDTO> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to find an asset with the ticker: {} and the goalId: {}", ticker, goalId)

        goalId.takeIf {
            serviceGoal.existsById(it)
        }?.also {
            val foundAsset = serviceAsset.findByTicker(ticker, goalId)

            logger.info("[$methodName] - Asset found: {} ", foundAsset)

            return ResponseEntity.ok(foundAsset.toResponseDTO())
        }

        throw goalNotFoundException(goalId = goalId)
    }

    @DeleteMapping("/{assetId}")
    fun deleteAsset(@PathVariable("assetId") assetId: Long): ResponseEntity<String> {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.info("[$methodName] - Starting to delete an asset with the assetId: {}", assetId)

        assetId.takeIf {
            serviceAsset.existsById(it)
        }?.also {
            serviceAsset.delete(it)

            logger.info("[$methodName] - Asset deleted")

            return ResponseEntity.ok("OK")
        }

        throw assetNotFoundException(assetId = assetId)
    }

    @Throws(ResponseStatusException::class)
    private fun assetNotFoundException(assetId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Asset not found with id: {} ", assetId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Asset not found")
    }

    @Throws(ResponseStatusException::class)
    private fun goalNotFoundException(goalId: Long?): ResponseStatusException {
        val methodName = object {}.javaClass.enclosingMethod.name
        logger.error("[$methodName] - Goal not found with id: {} ", goalId)
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found")
    }

}
