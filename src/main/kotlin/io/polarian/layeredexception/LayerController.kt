package io.polarian.layeredexception

import io.polarian.layeredexception.exception.InternalErrorCode
import io.polarian.layeredexception.exception.RepositoryException
import io.polarian.layeredexception.exception.ServiceException
import io.polarian.layeredexception.repository.DataAccessErrorCode
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("layer")
class LayerController(val layerService: LayerService) {

    @GetMapping("unknown")
    fun getUnknownException(): String {
        // UNKNOWN_ERROR
        return layerService.throwUnknownException()
    }

    @GetMapping("known")
    fun getKnownException(): Pair<String, String> {
        // NOT_FOUND_ERROR
        return layerService.throwKnownException()
    }

    @GetMapping("internal")
    fun getInternal(): String {
        // PERM_ERROR
        return layerService.throwInternalException()
    }

    @GetMapping("codemap")
    fun getCodeMap(): CodeMap {
        return DataAccessErrorCode.codeMap
    }

    @GetMapping("codemap2")
    fun getCodeMap2(): CodeMap {
        return layerService.getCodeMap()
    }

    @GetMapping("enummap")
    fun getEnumMap(): EnumMap {
        return DataAccessErrorCode.enumMap
    }

    @GetMapping("direct")
    fun getDirectException(): String {
        // NOT_FOUND_ERROR
        throw ServiceException(RepositoryException(RuntimeException(), 1, null), InternalErrorCode.NOT_FOUND_ERROR)
    }

    @GetMapping("direct2")
    fun getDirectException2(): String {
        // NOT_FOUND_ERROR
        return layerService.getDirectException()
    }
}