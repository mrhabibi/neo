package com.mrhabibi.neo.validator

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mrhabibi on 11/1/17.
 */
class DateValidator : ContinuousValidator() {

    override fun parseValue(roughValue: String): Number {
        return convertVersionToLong(roughValue)
    }

    private fun convertVersionToLong(dateString: String): Long = sdf.parse(dateString).time

    companion object {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
}