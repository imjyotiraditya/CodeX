package dev.jyotiraditya.codex.util

import java.util.Locale

/**
 * String extensions
 */
object StringExtensions {
    /**
     * Capitalize the first letter of a string
     */
    fun String.capitalize(locale: Locale = Locale.getDefault()): String {
        return this.lowercase(locale)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(locale) else it.toString() }
    }
}