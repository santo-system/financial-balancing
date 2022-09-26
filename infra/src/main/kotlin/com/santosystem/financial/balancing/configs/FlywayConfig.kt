package com.santosystem.financial.balancing.configs

import org.flywaydb.core.Flyway
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import javax.sql.DataSource

private const val LOCATION = "db/migration"
private const val FLYWAY_TABLE = "flyway_schema_history"

@Component
class FlywayConfig {

    @Bean(initMethod = "migrate")
    fun startMigrate() {
        Flyway.configure()
            .baselineOnMigrate(true)
            .validateOnMigrate(true)
            .dataSource(getDataSource())
            .table(FLYWAY_TABLE)
            .locations(LOCATION)
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    fun getDataSource(): DataSource? {
        return DataSourceBuilder.create().build()
    }
}
