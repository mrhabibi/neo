package com.bukalapak.android.neo.validator

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by mrhabibi on 5/9/17.
 */

open class ContinuousValidator : NeoValidator<Number>() {

    override fun parseValue(roughValue: String): Number {
        return java.lang.Double.valueOf(roughValue)
    }

    override fun <U> isFulfilled(value: U, phrase: String): Boolean {

        // < 0
        val lessThan = getMatcher("(< )(.+)", phrase)

        // <= 0
        val lessThanOrEqualsTo = getMatcher("(<= )(.+)", phrase)

        // > 0
        val moreThan = getMatcher("(> )(.+)", phrase)

        // >= 0
        val moreThanOrEqualsTo = getMatcher("(>= )(.+)", phrase)

        // 0
        // = 0
        // == 0
        // === 0
        val equalsTo = getMatcher("(= |== |=== )?(-?[0-9]+(\\.[0-9]+)?)", phrase)

        // <> 0
        // >< 0
        // != 0
        val not = getMatcher("(<> |!= |>< )(.+)", phrase)

        // 0 - 0
        // 0 -> 0
        val to = getMatcher("(.+)( - | -> )(.+)", phrase)

        // % 0 = 0
        // % 0 == 0
        // % 0 === 0
        // mod 0 = 0
        // mod 0 == 0
        // mod 0 === 0
        val mod = getMatcher("(% |mod )(.+)( = | == | === )(.+)", phrase)

        val concreteValue = (value as Number).toDouble()

        val fulfilled: Boolean
        if (lessThan.matches()) {
            val `val` = parseValue(lessThan.group(2)).toDouble()
            fulfilled = concreteValue < `val`
        } else if (lessThanOrEqualsTo.matches()) {
            val `val` = parseValue(lessThanOrEqualsTo.group(2)).toDouble()
            fulfilled = concreteValue <= `val`
        } else if (moreThan.matches()) {
            val `val` = parseValue(moreThan.group(2)).toDouble()
            fulfilled = concreteValue > `val`
        } else if (moreThanOrEqualsTo.matches()) {
            val `val` = parseValue(moreThanOrEqualsTo.group(2)).toDouble()
            fulfilled = concreteValue >= `val`
        } else if (equalsTo.matches()) {
            val `val` = parseValue(equalsTo.group(2)).toDouble()
            fulfilled = concreteValue == `val`
        } else if (not.matches()) {
            val `val` = parseValue(not.group(2)).toDouble()
            fulfilled = concreteValue != `val`
        } else if (to.matches()) {
            val val1 = parseValue(to.group(1)).toDouble()
            val val2 = parseValue(to.group(3)).toDouble()
            fulfilled = concreteValue >= val1 && concreteValue <= val2
        } else if (mod.matches()) {
            val val1 = parseValue(mod.group(2)).toDouble()
            val val2 = parseValue(mod.group(4)).toDouble()
            fulfilled = concreteValue % val1 == val2
        } else {
            fulfilled = phrase.equals(value.toString(), ignoreCase = true)
        }
        return fulfilled
    }

    private fun getMatcher(regex: String, text: String): Matcher {
        val pattern = Pattern.compile(regex)
        return pattern.matcher(text)
    }
}
