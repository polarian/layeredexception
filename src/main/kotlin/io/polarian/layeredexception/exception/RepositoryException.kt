package io.polarian.layeredexception.exception

import org.aspectj.lang.Signature

/* DELETE ME
//class RepositoryException(e: RuntimeException? = null, val errorCode : RepositoryErrorCode) : RuntimeException(e)

enum class RepositoryErrorCode {
    EMPTY_RESULT_DATA_ACCESS_EXCEPTION,
    DATA_ACCESS_EXCEPTION,
    DUPLICATE_KEY_EXCEPTION,
    SECURITY_EXCEPTION
}
*/
class RepositoryException(e: RuntimeException? = null, val errorCode: Int = 0, val origin: Signature? = null) : RuntimeException(e)