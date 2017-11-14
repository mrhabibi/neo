package com.mrhabibi.neo.model

import com.mrhabibi.neo.validator.NeoValidator

/**
 * Created by mrhabibi on 10/30/17.
 */
data class NeoVariable(var key: String,
                       var value: Function0<*>,
                       var validator: NeoValidator<*>)