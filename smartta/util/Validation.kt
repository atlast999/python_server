package vn.com.vti.smartta.util

import android.util.Patterns

fun String.isEmail() = this.matches(Patterns.EMAIL_ADDRESS.toRegex())

@Suppress("unused")
fun String.isValidPassword() = true //TODO password validation

fun String.isValidPhoneNumber() = this[0].toString() == "0" && this.length >= 10

fun String.isValidFullName() = this.length >= 5
