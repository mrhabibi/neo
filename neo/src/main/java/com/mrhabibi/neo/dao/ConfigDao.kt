package com.mrhabibi.neo.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mrhabibi.neo.model.NeoConfig

/**
 * Created by mrhabibi on 10/26/17.
 */
@Dao
interface ConfigDao : NeoDao<NeoConfig> {

    @Query("SELECT * FROM config")
    override fun get(): List<NeoConfig>

}