package io.polarian.layeredexception.repository

import org.springframework.dao.*

val DataAccessException.errorCode: Int get() = DataAccessErrorCode.valueOf(this).code
