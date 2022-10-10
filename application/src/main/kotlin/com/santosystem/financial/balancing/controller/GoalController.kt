package com.santosystem.financial.balancing.controller

import com.santosystem.financial.balancing.dto.request.GoalRequestDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO
import com.santosystem.financial.balancing.dto.response.GoalResponseDTO.Companion.toResponseDTO
import com.santosystem.financial.balancing.port.service.GoalService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/goals")
class GoalController() {

    private val logger = LoggerFactory.getLogger(GoalController::class.java)

    @Autowired
    private lateinit var service: GoalService

    @PostMapping
    fun createGoal(
        @Valid @RequestBody request: GoalRequestDTO
    ): ResponseEntity<GoalResponseDTO> {
        logger.info("Init to create the goal: {} ", request)

        val goal = service.save(request.toDomain())

        val response = goal.toResponseDTO()

        logger.info("Goal created: {} ", response)

        return ResponseEntity.ok(response)
    }

}
