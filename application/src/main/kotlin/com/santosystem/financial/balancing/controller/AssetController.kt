package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.AssetRequestDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO
import com.santosystem.financial.balancing.dto.response.AssetResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.AssetService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/assets")
class AssetController() {

    private val logger = LoggerFactory.getLogger(AssetController::class.java)

    @Autowired
    private lateinit var service: AssetService

    @PostMapping
    fun createGoal(
        @Valid @RequestBody request: AssetRequestDTO
    ): ResponseEntity<AssetResponseDTO> {
        logger.info("Init to create the asset: {} ", request)

        val asset = service.save(request.toDomain())

        logger.info("Asset created: {} ", asset)

        return ResponseEntity.ok(asset.toResponseDTO())
    }

}
