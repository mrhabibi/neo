package com.bukalapak.android.neo.dao

import android.arch.persistence.room.*
import com.bukalapak.android.neo.model.NeoModel

/**
 * Created by mrhabibi on 10/30/17.
 */
@Dao
interface NeoDao<T : NeoModel> {

    fun get(): List<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun put(data: ArrayList<T>)

}