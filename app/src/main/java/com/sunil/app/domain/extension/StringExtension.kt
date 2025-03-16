@file:JvmName("StringUtils")

package com.sunil.app.domain.extension

import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Patterns
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sunil.app.data.utils.NetworkHandler
import java.util.Locale
import javax.inject.Inject


/**
 *  Utility functions for string manipulation and JSON conversion.
 */

object JsonUtils {
    private val gson: Gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .create()

    /**
     * Returns the Gson instance.
     *
     * @return The Gson instance.
     */
    fun getGsonInstance(): Gson {
        return gson
    }
}

/**
 * Converts a string to an integer safely, returning a default value if the conversion fails.
 *
 * @param default The default value to return if the string is not a valid integer.
 * @return The integer representation of the string, or the default value if the conversion fails.
 */
fun String.toIntOrDefault(default: Int = -1): Int = toIntOrNull() ?: default

/**
 * Converts a string to a boolean safely, returning a default value if the conversion fails.
 *
 * @param default The default value to return if the string is not a valid boolean ("true" or "false").
 * @return The boolean representation of the string, or the default value if the conversion fails.
 */
fun String.toBooleanOrDefault(default: Boolean = false): Boolean =
    toBooleanStrictOrNull() ?: default


/**
 * Checks if an integer is negative.
 */
val Int.isNegative get() = this < 0

/**
 * Converts a boolean to an integer (1 for true, 0 for false).
 */
val Boolean.intValue get() = if (this) 1 else 0

/**
 * Converts a string to title case (e.g., "hello world" becomes "Hello World").
 *
 * @return The string in title case.
 */
fun String.toTitleCase(): String = trim().split("\\s+".toRegex())
    .filter { it.isNotEmpty() }
    .joinToString(" ") { it ->
        it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        .lowercase(Locale.getDefault()) }

/**
 * Executes a lambda if the string is not null and not empty.
 *
 * @param string The lambda to execute if the string is not null and not empty.
 */
fun String?.ifNotEmpty(string: String.() -> Unit) {
    this?.takeIf { it.isNotEmpty() }?.also(string)
}

/**
 * Checks if a string contains any letters.
 */
val String.hasLetters get() = any { it.isLetter() }

/**
 * Checks if a string contains any numbers.
 */
val String.hasNumbers get() = any { it.isDigit() }

/**
 * Checks if a string is a valid password (at least 8 characters, contains letters and numbers).
 */
val String.isValidPassword get() = length >= 8 && hasLetters && hasNumbers

/**
 * Checks if a string contains only letters.
 */
val String.isAlphabetic get() = all { it.isLetter() }

/**
 * Checks if a string is a valid email address.
 *
 * @return True if the string is a valid email address, false otherwise.
 */
fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Checks if a string is a valid URL.
 *
 * @return True if the string is a valid URL, false otherwise.
 */
fun String.isUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

/**
 * Joins a variable number of parameters into a single string.
 *
 * @param params The parameters to join.
 * @return The joined string.
 */
fun join(vararg params: Any?) = params.joinToString()

/**
 * Converts an object to a JSON string.
 *
 * @return The JSON string representation of the object, or null if an error occurs.
 */
fun Any.toJsonString(): String? = runCatching {
    JsonUtils.getGsonInstance().toJson(this)
}.getOrNull()

/**
 * Converts a JSON string to an object of type T.
 *
 * @return The object of type T, or null if the conversion fails.
 */
inline fun <reified T : Any> String?.fromJsonString(): T? = runCatching {
    JsonUtils.getGsonInstance().fromJson(this, T::class.java)
}.getOrNull()

/**
 * Converts a string to an Editable.
 *
 * @return The Editable representation of the string.
 */
fun String.toEditable(): Editable = SpannableStringBuilder(this)

/**
 * Repeats an action a specified number of times.
 *
 * @param predicate The action to repeat.
 */
fun Int.repeatAction(predicate: (Int) -> Unit) = repeat(this, predicate)

/**
 * Checks if a string is a valid JSON string (starts with '{' or '[').
 */
val String.isJsonString: Boolean
    get() = trim().let { it.isNotBlank() && (it.startsWith("{") || it.startsWith("[")) }

/**
 * Converts a JSON string to an object of type T, using a TypeToken to handle generic types.
 *
 * @return The object of type T, or null if the conversion fails.
 */
fun <T> String?.fromTypedJson(): T? {
    val type = object : TypeToken<T>() {}.type
    return runCatching {
        JsonUtils.getGsonInstance().fromJson<T>(this, type)
    }.getOrNull()
}

/**
 * Converts an object to a JSON string, using a TypeToken to handle generic types.
 *
 * @return The JSON string representation of the object.
 */
fun <T> Any?.toTypedJson(): String? = runCatching {
    val type = object : TypeToken<T>() {}.type
    JsonUtils.getGsonInstance().toJson(this, type)
}.getOrNull()

/**
 * Creates a deep copy of an object using JSON serialization and deserialization.
 *
 * @return A deep copy of the object.
 */
inline fun <reified T> T.deepCopy(): T? = runCatching {
    val stringProject = JsonUtils.getGsonInstance().toJson(this, T::class.java)
    JsonUtils.getGsonInstance().fromJson(stringProject, T::class.java)
}.getOrNull()

/**
 * Converts a string to uppercase using the specified locale.
 *
 * @param locale The locale to use for the conversion.
 * @return The uppercase string.
 */
fun String.upperCase(locale: Locale = Locale.getDefault()) = uppercase(locale)

/**
 * Converts a string to lowercase using the specified locale.
 *
 * @param locale The locale to use for the conversion.
 * @return The lowercase string.
 */
fun String.lowerCase(locale: Locale = Locale.getDefault()) = lowercase(locale)
