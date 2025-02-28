package com.sunil.app.domain.model

import java.io.IOException


sealed class ErrorType : IOException() {
    data object NoConnectivityError : ErrorType()
    data object SocketTimeoutException : ErrorType()
    data object SSLHandshakeException : ErrorType()
    data object JsonEncodingException : ErrorType()
    data object DefaultError : ErrorType()
    class ServerError(var status: Int, var code: Int = 0, var desc: String) : ErrorType()
    class FormValidationError(var desc: String) : ErrorType()
}
