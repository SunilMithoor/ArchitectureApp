package com.sunil.app.domain.model

import java.io.IOException


/**
 * Represents different types of errors that can occur in the application.
 *
 * This sealed class provides a type-safe way to handle various error scenarios.
 */
sealed class ErrorType : IOException() {

    /**
     * Represents an error when there is no network connectivity.
     */
    data object NoConnectivity : ErrorType() {
        private fun readResolve(): Any = NoConnectivity
    }

    /**
     * Represents an error when a socket timeout occurs.
     */
    data object SocketTimeout : ErrorType()

    /**
     * Represents an error during the SSL handshake process.
     */
    data object SSLHandshake : ErrorType()

    /**
     * Represents an error during JSON encoding or decoding.
     */
    data object JsonEncoding : ErrorType()

    /**
     * Represents a generic or default error.
     */
    data object Default : ErrorType()

    /**
     * Represents an error returned by the server.
     *
     * @property httpStatus The HTTP status code returned by the server.
     * @property errorCode An optional error code provided by the server.
     * @property message A description of the error.
     */
    data class Server(
        val httpStatus: Int,
        val errorCode: Int? = null,
        override val message: String = ""
    ) : ErrorType()

    /**
     * Represents an error due to form validation failure.
     *
     * @property message A description of the validation error.
     */
    data class FormValidation(override var message: String) : ErrorType()

}
