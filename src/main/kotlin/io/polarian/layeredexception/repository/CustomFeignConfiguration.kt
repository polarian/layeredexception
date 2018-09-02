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
import feign.Response
import feign.codec.DecodeException
import feign.codec.ErrorDecoder
import io.polarian.layeredexception.exception.DecodedFeignException
import org.springframework.context.annotation.Bean

class CustomFeignConfiguration : FeignConfiguration() {
    @Bean
    override fun feignErrorDecoder(): ErrorDecoder {
        return CustomErrorDecoder("")
    }

    class CustomErrorDecoder(private val jsonPath: String) : ErrorDecoder {
        private val objectMapper: ObjectMapper = jacksonObjectMapper()

        override fun decode(methodKey: String?, response: Response?): Exception {
            return try {
                val host = objectMapper.readTree(response?.body()?.asInputStream()).at("$jsonPath/host").asText()
                val code = objectMapper.readTree(response?.body()?.asInputStream()).at("$jsonPath/code").asText()

                DecodedFeignException("$host-$code", methodKey ?: "")
            } catch (e: Exception) {
                throw DecodeException("invalid error response", e)
            }
        }
    }
}