package io.polarian.layeredexception

interface LayerService {
    fun throwUnknownException(): String
    fun throwKnownException(): Pair<String, String>
    fun throwInternalException(): String
    fun getCodeMap(): CodeMap
    fun getDirectException(): String
}
