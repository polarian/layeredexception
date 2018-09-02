package io.polarian.layeredexception.exception

import org.springframework.http.HttpStatus

enum class InternalErrorCode(val code: String, val httpStatus: HttpStatus) {
    UNKNOWN_ERROR("000", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_ERROR("001", HttpStatus.BAD_REQUEST),
    FATAL_ERROR("002", HttpStatus.INTERNAL_SERVER_ERROR),
    PERM_ERROR("003", HttpStatus.FORBIDDEN),
    NETWORK_ERROR("004", HttpStatus.BAD_GATEWAY),
    LOGICAL_ERROR("005", HttpStatus.INTERNAL_SERVER_ERROR);
}

enum class ExternalErrorCode(val code: String, val internalErrorCode: InternalErrorCode) {
    LAYER_UNKNOWN_ERROR("LAYER-000", InternalErrorCode.UNKNOWN_ERROR),
    LAYER_NOT_FOUND_ERROR("LAYER-001", InternalErrorCode.NOT_FOUND_ERROR),
    LAYER_FATAL_ERROR("LAYER-002", InternalErrorCode.FATAL_ERROR),
    LAYER_PERM_ERROR("LAYER-003", InternalErrorCode.PERM_ERROR),
    LAYER_NETWORK_ERROR("LAYER-004", InternalErrorCode.NETWORK_ERROR),
    LAYER_LOGICAL_ERROR("LAYER-005", InternalErrorCode.LOGICAL_ERROR);

    companion object {
        fun getExternalErrorCode(host: String, code: String): ExternalErrorCode {
            return ExternalErrorCode.values().find {
                it.code == "$host-$code"
            } ?: LAYER_UNKNOWN_ERROR
        }
    }
}
