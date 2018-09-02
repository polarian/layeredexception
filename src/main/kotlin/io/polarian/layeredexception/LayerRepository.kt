package io.polarian.layeredexception

interface LayerRepository {
    fun throwIllegalArgumentException(): String
    fun throwEmptyResultDataAccessException(): Pair<String, String>
    fun throwSecurityException(): String
    fun getCodeMap(): CodeMap
    fun getDirectException(): String
}
