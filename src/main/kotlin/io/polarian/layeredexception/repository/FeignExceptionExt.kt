package io.polarian.layeredexception.repository

import feign.FeignException

val FeignException.errorCode: Int get() = FeignErrorCode.valueOf(this).code
