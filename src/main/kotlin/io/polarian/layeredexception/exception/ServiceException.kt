package io.polarian.layeredexception.exception

class ServiceException(e: RuntimeException? = null, val errorCode: InternalErrorCode) : RuntimeException(e)