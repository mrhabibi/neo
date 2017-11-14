package com.bukalapak.android.neo.model

import com.bukalapak.android.neo.validator.NeoValidator

/**
 * Created by mrhabibi on 10/30/17.
 */
data class NeoVariable(var key: String,
                       var value: Function0<*>,
                       var validator: NeoValidator<*>)