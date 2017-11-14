package com.bukalapak.android.neo.validator

/**
 * Created by mrhabibi on 5/9/17.
 */

class DiscreteValidator : NeoValidator<Any>() {

    override fun <U> isFulfilled(value: U, phrase: String): Boolean = phrase.equals(value.toString(), ignoreCase = true)

}
