package com.santosystem.financial.balancing.util.reader

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class ReaderUtil {
    companion object {
        private val objectMapper: ObjectMapper = jacksonObjectMapper().registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        ).registerModule(JavaTimeModule())

        fun String.readJsonFromPath(): String =
            Thread.currentThread().contextClassLoader.getResource(this)?.readText()!!

        @Throws(JsonProcessingException::class, JsonMappingException::class)
        fun <T> String.fromJson(parse: Class<T>): T = objectMapper.readValue(this, parse)

        @Throws(JsonProcessingException::class, JsonMappingException::class)
        fun <T> String.fromJson(parse: TypeReference<T>): T = objectMapper.readValue(this, parse)

        fun Any.toJson(): String = objectMapper.writeValueAsString(this)

    }
}
