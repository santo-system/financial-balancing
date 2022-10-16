package com.santosystem.financial.balancing.config

import com.santosystem.financial.balancing.exception.InfraUnexpectedException
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

private const val LOCATION_STRUCTURE = "db/migration/structure"

private const val LOCATION_DATA = "db/migration/data"

@Configuration
class FlywayConfig {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    fun getDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @DependsOn("getDataSource")
    @Bean(initMethod = "migrate")
    fun startMigrate() {
        runCatching {
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(getDataSource())
                .locations(LOCATION_STRUCTURE, LOCATION_DATA)
                .load()
                .migrate()
        }.onFailure {
            logger.error("Failed to init Flyway. Original message: {}", it.message)
            throw InfraUnexpectedException("Failed to init Flyway")
        }
    }
}
