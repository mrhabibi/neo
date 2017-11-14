package com.bukalapak.android.neo

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.bukalapak.android.neo.dao.ConfigDao
import com.bukalapak.android.neo.dao.PreferenceDao
import com.bukalapak.android.neo.dao.ToggleDao
import com.bukalapak.android.neo.model.*

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