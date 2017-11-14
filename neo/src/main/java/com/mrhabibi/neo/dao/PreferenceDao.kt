package com.bukalapak.android.neo.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.bukalapak.android.neo.model.NeoPreference

/**
 * Created by mrhabibi on 10/26/17.
 */
@Dao
interface PreferenceDao : NeoDao<NeoPreference> {

    @Query("SELECT * FROM preference")
    override fun get(): List<NeoPreference>

    @Query("DELETE FROM preference")
    fun clear()
}