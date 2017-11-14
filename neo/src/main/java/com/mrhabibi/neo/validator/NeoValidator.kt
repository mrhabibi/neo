package com.bukalapak.android.neo.validator

/**
 * Created by mrhabibi on 5/9/17.
 */

abstract class NeoValidator<T> {

    abstract fun <U> isFulfilled(value: U, phrase: String): Boolean

    open fun parseValue(roughValue: String): T = roughValue as T

}
