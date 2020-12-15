package vn.com.vti.smartta.model.pojo.schedule.request

import androidx.annotation.IntRange
import vn.com.vti.common.util.extension.DateTimeXs.toTimeString
import vn.com.vti.smartta.model.const.API_PATTERN_DATETIME_PARAM_GET
import vn.com.vti.smartta.model.const.QueryKey
import vn.com.vti.smartta.model.pojo.IParamRequests
import java.text.SimpleDateFormat
import java.util.*

open class ScheduleRequest(
    open val scope: String,
    open @IntRange(from = 0) val fromMillis: Long,
    open @IntRange(from = 0) val toMillis: Long
) : IParamRequests {

    override fun buildInto(bundle: MutableMap<String, String>) {
        bundle.apply {
            put(QueryKey.TARGET, scope)
            SimpleDateFormat(API_PATTERN_DATETIME_PARAM_GET, Locale.getDefault()).let {
                put("from", fromMillis.toTimeString(it))
                put("to", toMillis.toTimeString(it))
            }
        }
    }
}

data class IdScopeScheduleRequest(
    val id: Int,
    override val scope: String,
    override val fromMillis: Long,
    override val toMillis: Long
) : ScheduleRequest(scope, fromMillis, toMillis) {

    override fun buildInto(bundle: MutableMap<String, String>) {
        super.buildInto(bundle)
        bundle.put("id", id.toString())
    }
}