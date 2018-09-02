package io.polarian.layeredexception.repository

import feign.FeignException
import feign.RetryableException
import feign.codec.DecodeException
import feign.codec.EncodeException
import io.polarian.layeredexception.CodeMap
import io.polarian.layeredexception.EnumMap
import io.polarian.layeredexception.exception.DecodedFeignException

enum class FeignErrorCode(val code: Int, val feignException: Class<out FeignException>) {
    DECODED_FEIGN_EXCEPTION(504, DecodedFeignException::class.java),
    RETRYABLE_EXCEPTION(501, RetryableException::class.java),
    DECODE_EXCEPTION(502, DecodeException::class.java),
    ENCODE_EXCEPTION(503, EncodeException::class.java),
    FEIGN_EXCEPTION(500, FeignException::class.java);

    companion object {
        fun valueOf(value: FeignException): FeignErrorCode {
            return FeignErrorCode.values().find {
                it.feignException == value.javaClass
            } ?: FEIGN_EXCEPTION
        }

        val codeMap: CodeMap
            get() {
                return mutableMapOf<String, String>().apply {
                    FeignErrorCode.values().map {
                        this.put(String.format("%03d", it.code), it.name)
                    }
                }
            }

        val enumMap: EnumMap
            get() {
                return mutableMapOf<String, String>().apply {
                    FeignErrorCode.values().map {
                        this.put(it.name, String.format("%03d", it.code))
                    }
                }
            }
    }
}