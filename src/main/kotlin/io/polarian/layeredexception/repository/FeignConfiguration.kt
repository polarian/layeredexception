/*
 * Copyright 2018 stpolarian@google.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.polarian.layeredexception.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Feign
import feign.Response
import feign.Retryer
import feign.codec.DecodeException
import feign.codec.Decoder
import feign.codec.Encoder
import feign.codec.ErrorDecoder
import feign.optionals.OptionalDecoder
import io.polarian.layeredexception.exception.DecodedFeignException
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

class FeignConfiguration : FeignClientsConfiguration() {
    private final val objectMapper = jacksonObjectMapper()
    private final val messageConverters = HttpMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
    private final val defaultDecoder = OptionalDecoder(ResponseEntityDecoder(SpringDecoder { messageConverters }))
    private final val defaultEncoder = SpringEncoder { messageConverters }

    @Bean
    override fun feignDecoder(): Decoder {
        return defaultDecoder
    }

    @Bean
    override fun feignEncoder(): Encoder {
        return defaultEncoder
    }

    @Bean
    override fun feignBuilder(retryer: Retryer): Feign.Builder {
        return Feign.builder().errorDecoder(feignErrorDecoder()).retryer(retryer)
    }

    @Bean
    fun feignErrorDecoder(): ErrorDecoder {
        return FeignErrorDecoder("code")
    }

    class FeignErrorDecoder(private val jsonPath: String) : ErrorDecoder {
        private val objectMapper: ObjectMapper = jacksonObjectMapper()

        override fun decode(methodKey: String?, response: Response?): Exception {
            return try {
                val code = objectMapper.readTree(response?.body()?.asInputStream()).get(jsonPath).asText()
                DecodedFeignException(code, methodKey ?: "")
            } catch (e: Exception) {
                throw DecodeException("invalid error response", e)
            }
        }
    }
}