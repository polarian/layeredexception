package io.polarian.layeredexception.repository

import io.polarian.layeredexception.CodeMap
import io.polarian.layeredexception.EnumMap
import org.springframework.dao.*
import org.springframework.jca.cci.CannotCreateRecordException
import org.springframework.jca.cci.CannotGetCciConnectionException
import org.springframework.jca.cci.CciOperationNotSupportedException
import org.springframework.jca.cci.RecordTypeNotSupportedException
import org.springframework.jdbc.*
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException
import org.springframework.jdbc.support.xml.SqlXmlFeatureNotImplementedException

enum class DataAccessErrorCode(val code: Int, val dataAccessException: Class<out DataAccessException>) {
    CANNOT_CREATE_RECORD_EXCEPTION(101, CannotCreateRecordException::class.java),
    CANNOT_GET_CCI_CONNECTION_EXCEPTION(102, CannotGetCciConnectionException::class.java),
    CANNOT_GET_JDBC_CONNECTION_EXCEPTION(103, CannotGetJdbcConnectionException::class.java),
    EMPTY_RESULT_DATA_ACCESS_EXCEPTION(114, EmptyResultDataAccessException::class.java),
    CANNOT_ACQUIRE_LOCK_EXCEPTION(105, CannotAcquireLockException::class.java),
    CANNOT_SERIALIZE_TRANSACTION_EXCEPTION(106, CannotSerializeTransactionException::class.java),
    DEADLOCK_LOSER_DATA_ACCESS_EXCEPTION(107, DeadlockLoserDataAccessException::class.java),
    JDBC_UPDATE_AFFECTED_INCORRECT_NUMBER_OF_ROWS_EXCEPTION(108, JdbcUpdateAffectedIncorrectNumberOfRowsException::class.java),
    BAD_SQL_GRAMMAR_EXCEPTION(201, BadSqlGrammarException::class.java),
    CCI_OPERATION_NOT_SUPPORTED_EXCEPTION(202, CciOperationNotSupportedException::class.java),
    DATA_ACCESS_RESOURCE_FAILURE_EXCEPTION(203, DataAccessResourceFailureException::class.java),
    DUPLICATE_KEY_EXCEPTION(204, DuplicateKeyException::class.java),
    INCORRECT_RESULT_SIZE_DATA_ACCESS_EXCEPTION(205, IncorrectResultSizeDataAccessException::class.java),
    INCORRECT_RESULT_SET_COLUMN_COUNT_EXCEPTION(206, IncorrectResultSetColumnCountException::class.java),
    INCORRECT_UPDATE_SEMANTICS_DATA_ACCESS_EXCEPTION(207, IncorrectUpdateSemanticsDataAccessException::class.java),
    INVALID_RESULT_SET_ACCESS_EXCEPTION(208, InvalidResultSetAccessException::class.java),
    LOB_RETRIEVAL_FAILURE_EXCEPTION(209, LobRetrievalFailureException::class.java),
    OPTIMISTIC_LOCKING_FAILURE_EXCEPTION(210, OptimisticLockingFailureException::class.java),
    PESSIMISTIC_LOCKING_FAILURE_EXCEPTION(211, PessimisticLockingFailureException::class.java),
    RECORD_TYPE_NOT_SUPPORTED_EXCEPTION(212, RecordTypeNotSupportedException::class.java),
    SQL_WARNING_EXCEPTION(213, SQLWarningException::class.java),
    SQL_XML_FEATURE_NOT_IMPLEMENTED_EXCEPTION(214, SqlXmlFeatureNotImplementedException::class.java),
    TYPE_MISMATCH_DATA_ACCESS_EXCEPTION(215, TypeMismatchDataAccessException::class.java),
    UNCATEGORIZED_SQL_EXCEPTION(216, UncategorizedSQLException::class.java),
    CLEANUP_FAILURE_DATA_ACCESS_EXCEPTION(301, CleanupFailureDataAccessException::class.java),
    CONCURRENCY_FAILURE_EXCEPTION(302, ConcurrencyFailureException::class.java),
    DATA_INTEGRITY_VIOLATION_EXCEPTION(303, DataIntegrityViolationException::class.java),
    DATA_RETRIEVAL_FAILURE_EXCEPTION(304, DataRetrievalFailureException::class.java),
    DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION(305, DataSourceLookupFailureException::class.java),
    INVALID_DATA_ACCESS_API_USAGE_EXCEPTION(306, InvalidDataAccessApiUsageException::class.java),
    INVALID_DATA_ACCESS_RESOURCE_USAGE_EXCEPTION(307, InvalidDataAccessResourceUsageException::class.java),
    NON_TRANSIENT_DATA_ACCESS_RESOURCE_EXCEPTION(308, NonTransientDataAccessResourceException::class.java),
    PERMISSION_DENIED_DATA_ACCESS_EXCEPTION(309, PermissionDeniedDataAccessException::class.java),
    QUERY_TIMEOUT_EXCEPTION(310, QueryTimeoutException::class.java),
    TRANSIENT_DATA_ACCESS_RESOURCE_EXCEPTION(311, TransientDataAccessResourceException::class.java),
    UNCATEGORIZED_DATA_ACCESS_EXCEPTION(312, UncategorizedDataAccessException::class.java),
    NON_TRANSIENT_DATA_ACCESS_EXCEPTION(401, NonTransientDataAccessException::class.java),
    RECOVERABLE_DATA_ACCESS_EXCEPTION(402, RecoverableDataAccessException::class.java),
    TRANSIENT_DATA_ACCESS_EXCEPTION(403, TransientDataAccessException::class.java),
    DATA_ACCESS_EXCEPTION(100, DataAccessException::class.java);

    companion object {
        fun valueOf(value: DataAccessException): DataAccessErrorCode {
            return DataAccessErrorCode.values().find {
                it.dataAccessException == value.javaClass
            } ?: DATA_ACCESS_EXCEPTION
        }

        val codeMap: CodeMap
            get() {
                return mutableMapOf<String, String>().apply {
                    DataAccessErrorCode.values().map {
                        this.put(String.format("%03d", it.code), it.name)
                    }
                }
            }

        val enumMap: EnumMap
            get() {
                return mutableMapOf<String, String>().apply {
                    DataAccessErrorCode.values().map {
                        this.put(it.name, String.format("%03d", it.code))
                    }
                }
            }
    }
}
