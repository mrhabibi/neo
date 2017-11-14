package com.bukalapak.android.neo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.bukalapak.android.neo.GSON
import com.google.gson.JsonElement

/**
 * Created by mrhabibi on 10/26/17.
 */
@Entity(tableName = "preference")
data class NeoPreference(@PrimaryKey @ColumnInfo(name = "id") var id: String) : NeoModel {

    @Ignore
    lateinit var obj: Any

    @Ignore
    var cls: Class<*>? = null

    @ColumnInfo(name = "data")
    var data: JsonElement? = null
        set(value) {
            if (cls != null) {
                obj = GSON.instance.fromJson(value, cls)

                // handling for structure mismatch
                field = GSON.instance.toJsonTree(obj)
            } else {
                field = value
            }
        }
        get() = if (field == null) GSON.instance.toJsonTree(obj) else field

    @Ignore
    constructor(id: String, obj: Any) : this(id) {
        this.obj = obj
        this.cls = obj::class.java
    }

    @Ignore
    constructor(id: String, data: JsonElement) : this(id) {
        this.id = id
        this.data = data
    }

    override fun combineWith(other: Any) {
        other as NeoPreference
        data = other.data
    }

    override fun obtainId(): String = id

    override fun hashCode(): Int = generateHashCode()

    override fun equals(other: Any?): Boolean = generateEquals(other)
}