package com.santosystem.financial.balancing.config

import org.flywaydb.core.Flyway
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

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    fun getDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @DependsOn("getDataSource")
    @Bean(initMethod = "migrate")
    fun startMigrate() {
        Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(getDataSource())
            .locations(LOCATION_STRUCTURE, LOCATION_DATA)
            .load()
            .migrate()
    }
}
