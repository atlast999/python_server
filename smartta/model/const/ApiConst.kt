package vn.com.vti.smartta.model.const

import androidx.annotation.StringDef
import vn.com.vti.common.util.extension.DateTimeXs.toDate

@StringDef(AuthEntity.EMAIL, AuthEntity.USERNAME)
@Retention(AnnotationRetention.SOURCE)
annotation class AuthEntity {

    companion object {
        const val EMAIL = "email"
        const val USERNAME = "username"
    }
}

@StringDef(
    QueryScope.ME,
    QueryScope.USER,
    QueryScope.DEPARTMENT,
    QueryScope.MY_DEPARTMENT
)
@Retention(AnnotationRetention.SOURCE)
annotation class QueryScope {

    companion object {
        const val ME = "me"
        const val USER = "user"
        const val MY_DEPARTMENT = "my-department"
        const val DEPARTMENT = "department"
    }
}

const val API_PATTERN_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val API_TEMP_PATTERN_DATETIME = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val API_PATTERN_DATETIME_PARAM_GET = "yyyy-MM-dd"

object QueryKey {

    const val TARGET = "target"
    const val ID = "id"
    const val DATE = "date"
}

fun apiDateTimeToMillis(time: String, pattern: String = API_PATTERN_DATETIME): Long {
    return if (time.isNotEmpty()) {
        time.toDate(pattern)?.time ?: 0L
    } else 0L
}