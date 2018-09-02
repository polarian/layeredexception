package io.polarian.layeredexception.exception

import feign.FeignException
import io.polarian.layeredexception.advice.RestErrorResponse

class DecodedFeignException(val code: String, message: String) : FeignException(message) {
    val restErrorResponse = RestErrorResponse(code)
}