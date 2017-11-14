package com.bukalapak.android.neo

import com.google.gson.Gson

/**
 * Created by mrhabibi on 11/4/17.
 */
class GSON {

    companion object {
        val instance: Gson by lazy {
            Gson()
        }
    }

}