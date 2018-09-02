package io.polarian.layeredexception

import io.polarian.layeredexception.repository.FeignConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "ExceptionLayerClient", url = "localhost:8080/layer", configuration = [FeignConfiguration::class])
interface ExceptionLayerClient {

    @GetMapping("internal", consumes = ["application/json"])
    fun getInternal(): String

    @GetMapping("unknown", consumes = ["application/json"])
    fun getUnknownException(): String

    @GetMapping("known", consumes = ["application/json"])
    fun getKnownException(): Pair<String, String>

    @GetMapping("error", consumes = ["application/json"])
    fun getError(): String

    @GetMapping("codemap", consumes = ["application/json"])
    fun getCodeMap(): CodeMap

    @GetMapping("enummap", consumes = ["application/json"])
    fun getEnumMap(): EnumMap

    @GetMapping("direct", consumes = ["application/json"])
    fun getDirectException(): String
}
