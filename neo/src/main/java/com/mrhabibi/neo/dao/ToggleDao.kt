package com.bukalapak.android.neo.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.bukalapak.android.neo.model.NeoToggle

/**
 * Created by mrhabibi on 10/26/17.
 */
@Dao
interface ToggleDao : NeoDao<NeoToggle> {

    @Query("SELECT * FROM toggle")
    override fun get(): List<NeoToggle>

}