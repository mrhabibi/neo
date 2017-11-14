package com.bukalapak.android.neo.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.bukalapak.android.neo.validator.NeoValidator

/**
 * Created by mrhabibi on 10/26/17.
 */
@Entity(tableName = "toggle")
data class NeoToggle(
        @PrimaryKey @ColumnInfo(name = "id") var id: String,
        @ColumnInfo(name = "active") var active: Boolean) : NeoModel {

    @ColumnInfo(name = "segmentation")
    var segmentation: Map<String, String>? = null

    @Ignore
    constructor(id: String, active: Boolean, segmentation: Map<String, String>?) : this(id, active) {
        this.segmentation = segmentation
    }

    fun isFeatureActivated(variables: Map<String, NeoVariable>): Boolean {
        var result = true
        val segmentation = segmentation

        if (segmentation?.isNotEmpty() == true) {
            segmentation.filter { segment ->
                val key = segment.key
                val value = segment.value
                val variable = variables.get(key)
                variable != null && !isFulfilled(value, variable.value.invoke().toString(), variable.validator)
            }.forEach { result = false }
        }
        return active && result
    }

    fun isFulfilled(value: String, comparationValue: String?, validator: NeoValidator<*>?): Boolean {
        return comparationValue == null || validator == null || validator.isFulfilled(validator.parseValue(comparationValue), value)
    }

    override fun obtainId(): String = id

    override fun combineWith(other: Any) {
        other as NeoToggle
        active = other.active
        segmentation = other.segmentation
    }

    override fun hashCode(): Int = generateHashCode()

    override fun equals(other: Any?): Boolean = generateEquals(other)
}