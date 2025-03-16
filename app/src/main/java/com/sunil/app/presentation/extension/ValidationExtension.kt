package com.sunil.app.presentation.extension

import android.util.Patterns
import java.util.regex.Pattern


object ValidationUtils {

    /**
     * Checks if the given email string is valid.
     *
     * Uses the built-in `Patterns.EMAIL_ADDRESS` for a more robust and standard email validation.
     *
     * @param email The email string to validate.
     * @return True if the email is valid, false otherwise.
     */
    fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrBlank()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    /**
     * Checks if the given phone number string is valid.
     *
     * This function now checks for non-numeric characters and ensures the length is exactly 10.
     * It also handles null or blank inputs.
     *
     * @param phone The phone number string to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    fun isValidMobile(phone: String?): Boolean {
        if (phone.isNullOrBlank()) {
            return false
        }
        return phone.length == 10 && phone.all { it.isDigit() }
    }

    /**
     * Checks if the given password string meets the specified criteria.
     *
     * The password must contain at least:
     * - 1 digit
     * - 1 lowercase letter
     * - 1 uppercase letter
     * - 1 special character
     * - No whitespace
     * - At least 7 characters
     *
     * @param password The password string to validate.
     * @return True if the password is valid, false otherwise.
     */
    fun isValidPassword(password: String): Boolean {
        if (password.length < 7) {
            return false
        }
        val passwordREGEX = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$"
        )
        return passwordREGEX.matcher(password).matches()
    }

    /**
     * Checks if the given string represents a valid decimal number.
     *
     * This function now uses a more concise regex and handles null or blank inputs.
     *
     * @param data The string to check.
     * @return True if the string is a valid decimal, false otherwise.
     */
    fun isValidDecimal(data: String?): Boolean {
        if (data.isNullOrBlank()) {
            return false
        }
        val regex = "^\\d*\\.?\\d+$"
        return data.matches(regex.toRegex())
    }

    /**
     * Checks if the given string contains only digits.
     *
     * This function now handles null or blank inputs.
     *
     * @param data The string to check.
     * @return True if the string contains only digits, false otherwise.
     */
    fun isNumeric(data: String?): Boolean {
        if (data.isNullOrBlank()) {
            return false
        }
        val regex = "^[0-9]+$"
        return data.matches(regex.toRegex())
    }
}
