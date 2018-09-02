package io.polarian.layeredexception

import io.polarian.layeredexception.exception.InternalErrorCode
import io.polarian.layeredexception.exception.RepositoryException
import io.polarian.layeredexception.exception.ServiceException
import org.springframework.stereotype.Service

@Service
class ExceptionLayerService(val layerRepository: LayerRepository) : LayerService {
    override fun throwUnknownException(): String {
        return layerRepository.throwIllegalArgumentException()
    }

    override fun throwInternalException(): String {
        return try {
            layerRepository.throwSecurityException()
        } catch (e : SecurityException) {
            throw ServiceException(e, InternalErrorCode.PERM_ERROR)
        }
    }

    override fun throwKnownException(): Pair<String, String> {
        return try {
            layerRepository.throwEmptyResultDataAccessException()
        } catch (e : RepositoryException) {
            throw ServiceException(e, InternalErrorCode.NOT_FOUND_ERROR)
        }
    }

    override fun getCodeMap(): CodeMap {
        return try {
            layerRepository.getCodeMap()
        } catch (e : RepositoryException) {
            throw ServiceException(e, InternalErrorCode.PERM_ERROR)
        }
    }

    override fun getDirectException(): String {
        return try {
            layerRepository.getDirectException()
        } catch(e : RepositoryException) {
            throw ServiceException(e, InternalErrorCode.NETWORK_ERROR)
        }
    }
}