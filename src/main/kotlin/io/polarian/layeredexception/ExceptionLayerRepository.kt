package io.polarian.layeredexception

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class ExceptionLayerRepository(val jdbcTemplate: JdbcTemplate,
                               val exceptionLayerClient: ExceptionLayerClient) : LayerRepository {
    override fun throwIllegalArgumentException(): String {
        throw IllegalArgumentException()
    }

    override fun throwEmptyResultDataAccessException(): Pair<String, String> {
        return jdbcTemplate.queryForObject("SELECT application, profile FROM PROPERTIES WHERE application=1") { rs, _ -> Pair<String, String>(rs.getString(1), rs.getString(2)) }!!
    }

    override fun throwSecurityException(): String {
        throw SecurityException()
    }

    override fun getCodeMap(): CodeMap {
        return exceptionLayerClient.getCodeMap()
    }

    override fun getDirectException(): String {
        return exceptionLayerClient.getDirectException()
    }

    fun getList(): List<Pair<String, String>> {
        return jdbcTemplate.query("SELECT application, profile FROM PROPERTIES") { rs, _ ->
            Pair<String, String>(rs.getString(1), rs.getString(2))
        }
    }
}