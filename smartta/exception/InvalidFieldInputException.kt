package vn.com.vti.smartta.exception

import androidx.annotation.StringRes
import vn.com.vti.common.util.AppResources

class InvalidFieldInputException : IllegalArgumentException {

    private val code: Int

    constructor(code: Int, message: String) : super(message) {
        this.code = code
    }

    constructor(code: Int, @StringRes message: Int) : this(code, AppResources.getString(message))

}

object ValidationCode {
    const val ERROR_EMAIL_INVALID = 0x1
    const val ERROR_PASSWORD_INVALID = 0x2
    const val ERROR_PHONE_INVALID = 0x3
    const val ERROR_NAME_INVALID = 0x4
    const val ERROR_OTP_INVALID = 0x5
}