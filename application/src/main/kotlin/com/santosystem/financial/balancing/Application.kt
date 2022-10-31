package com.santosystem.financial.balancing

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Financial Balancing API",
        description = "Serviço responsável pelo balanceamento financeiro",
        version = "1.0.0",
    )
)
@EnableFeignClients
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
