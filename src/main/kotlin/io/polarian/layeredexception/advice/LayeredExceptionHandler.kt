package io.polarian.layeredexception.advice

import feign.FeignException
import io.polarian.layeredexception.exception.*
import io.polarian.layeredexception.repository.errorCode
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.dao.DataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@Aspect
@RestControllerAdvice
@ResponseBody
class LayeredExceptionHandler {
    @ExceptionHandler(ServiceException::class)
    fun serviceExceptionHandler(e: ServiceException): ResponseEntity<RestErrorResponse> {
        //TODO : REFACTOR - external and internal
        val externalErrorCode = try {
            ExternalErrorCode.getExternalErrorCode("LAYER", (e.cause?.cause as DecodedFeignException).code)
        } catch (e : RuntimeException) {
            ExternalErrorCode.LAYER_UNKNOWN_ERROR
        }

        val errorCode = if (externalErrorCode != ExternalErrorCode.LAYER_UNKNOWN_ERROR) {
            externalErrorCode.internalErrorCode
        } else {
            e.errorCode
        }

        return ResponseEntity(RestErrorResponse(errorCode.code), errorCode.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun unknownExceptionHandler(e: Exception): ResponseEntity<RestErrorResponse> {
        return ResponseEntity(RestErrorResponse(InternalErrorCode.UNKNOWN_ERROR.code), InternalErrorCode.UNKNOWN_ERROR.httpStatus)
    }

    @Around("@within(org.springframework.stereotype.Repository)")
    fun repositoryExceptionHandler(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            joinPoint.proceed()
        } catch (e: DataAccessException) {
            throw RepositoryException(e, e.errorCode, joinPoint.signature)
        } catch (e: FeignException) {
            throw RepositoryException(e, e.errorCode, joinPoint.signature)
        }
    }
}
