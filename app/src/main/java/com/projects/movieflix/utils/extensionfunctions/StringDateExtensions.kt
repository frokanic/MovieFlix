package com.projects.movieflix.utils.extensionfunctions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Reformat a date string from the current format to a new format.
 *
 * @param currentFormatPattern The current format of the date string, following the patterns of [SimpleDateFormat].
 * @param newFormatPattern The new format to which the date string will be converted, following the patterns of [SimpleDateFormat].
 * @return A string representing the date in the new format. If parsing fails, the original date string is returned.
 * @throws IllegalArgumentException If the given date string cannot be parsed into the [currentFormatPattern].
 *
 * Example usage:
 * ```
 * val originalDate = "24-03-2023"
 * val currentFormatPattern = "dd-MM-yyyy"
 * val newFormatPattern = "dd MMMM yyyy"
 * val reformattedDate = originalDate.reformatDate(currentFormatPattern, newFormatPattern)
 * // reformattedDate will be "24 March 2023"
 * ```
 */
fun String.reformatDate(currentFormatPattern: String, newFormatPattern: String): String {
    val originalFormat = SimpleDateFormat(currentFormatPattern, Locale.getDefault())
    val targetFormat = SimpleDateFormat(newFormatPattern, Locale.getDefault())
    return try {
        val date = originalFormat.parse(this) ?: return this
        targetFormat.format(date)
    } catch (e: IllegalArgumentException) {
        this
    }
}

/**
 * Converts a date string into epoch seconds based on the provided date format.
 * Epoch seconds represent the number of seconds that have elapsed since the Unix epoch (00:00:00 UTC on 1 January 1970).
 *
 * ### Accepted Formats
 * The function accepts any date format string compatible with `SimpleDateFormat` in Java.
 * The format string should follow the pattern rules defined by `SimpleDateFormat`.
 *
 * #### Common Format Components:
 * - `yyyy`: Year (e.g., 2021)
 * - `MM`: Month in year (e.g., 07 for July)
 * - `dd`: Day in month (e.g., 15)
 * - `HH`: Hour of the day (00-23)
 * - `mm`: Minute in hour
 * - `ss`: Second in minute
 *
 * #### Examples of Commonly Used Formats:
 * - `dd/MM/yyyy`: Day in month, month in year, year (e.g., 22/07/2021)
 * - `yyyy-MM-dd`: Year, month in year, day in month (e.g., 2021-07-22)
 * - `MM/dd/yyyy HH:mm:ss`: Month in year, day in month, year, hours, minutes, and seconds (e.g., 07/22/2021 12:05:30)
 *
 * @param format A `String` representing the date format to be used for parsing the input string.
 * @return The epoch seconds as `Long?`. Returns `null` if the parsing fails due to an incorrect format or other issues.
 *
 * ### Usage Example:
 * ```kotlin
 * val dateStr = "31/12/2020"
 * val format = "dd/MM/yyyy"
 * val epochSeconds = dateStr.toEpochSecondCompat(format)
 * println("Epoch seconds: $epochSeconds")
 * ```
 *
 * This example converts the date string "31/12/2020" into epoch seconds using the format "dd/MM/yyyy".
 *
 * ### Guidelines for Using Accepted Formats:
 * - Ensure the format matches the structure of your date string.
 * - Use the pattern symbols defined by `SimpleDateFormat` to construct your format string.
 * - Consider the locale and time zone. This function uses UTC as the time zone to standardize the epoch time conversion.
 */

fun String.toEpochSecondCompat(format: String): Long? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")

    return try {
        val date = sdf.parse(this)
        date?.let {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = it
            calendar.timeInMillis / 1000
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Calculates the remaining days from the current date to the specified date string.
 *
 * ### Parameters:
 * - `format`: The format of the date string, compatible with `SimpleDateFormat`.
 *
 * ### Returns:
 * - The number of days remaining until the specified date as `Long`.
 * - Returns `null` if the date parsing fails, or if the specified date is in the past.
 *
 * ### Accepted Formats:
 * The function accepts any date format string compatible with `SimpleDateFormat`. For example:
 * - "dd/MM/yyyy" for dates like "31/12/2020"
 * - "yyyy-MM-dd" for dates like "2020-12-31"
 *
 * ### Usage Example:
 * ```kotlin
 * val remainingDays = "31/12/2023".daysUntil("dd/MM/yyyy")
 * println("Days until target date: $remainingDays")
 * ```
 *
 * ### Note:
 * Ensure the format matches the structure of your date string. The calculation assumes the system's default timezone.
 */
fun String.daysUntil(format: String): Long? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.isLenient = false
    val targetDate = sdf.parse(this) ?: return null

    val currentDate = Calendar.getInstance()
    val targetCalendar = Calendar.getInstance().apply { time = targetDate }

    if (targetCalendar.before(currentDate)) {
        return null
    }

    val daysBetween = targetCalendar.timeInMillis - currentDate.timeInMillis
    return daysBetween / (24 * 60 * 60 * 1000)
}