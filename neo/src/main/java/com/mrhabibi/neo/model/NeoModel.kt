package com.bukalapak.android.neo.model

import android.arch.persistence.room.Entity

/**
 * Created by mrhabibi on 10/30/17.
 */
@Entity
interface NeoModel {

    fun combineWith(other: Any)

    fun obtainId(): String

    fun generateHashCode(): Int = obtainId().hashCode()

    fun generateEquals(other: Any?): Boolean {
        if (this == other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val obj = other as NeoModel
        return obtainId() == obj.obtainId()
    }
}