package com.mrhabibi.neo

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.mrhabibi.neo.dao.ConfigDao
import com.mrhabibi.neo.dao.PreferenceDao
import com.mrhabibi.neo.dao.ToggleDao
import com.mrhabibi.neo.model.*

/**
 * Created by mrhabibi on 10/26/17.
 */
@Database(entities = arrayOf(NeoToggle::class, NeoConfig::class, NeoPreference::class), version = 1)
@TypeConverters(Converter::class)
abstract class Atman : RoomDatabase() {

    abstract fun toggleDao(): ToggleDao
    abstract fun configDao(): ConfigDao
    abstract fun preferenceDao(): PreferenceDao

}