package com.mrhabibi.neo

import android.arch.persistence.room.Room
import android.content.Context
import com.mrhabibi.neo.dao.NeoDao
import com.mrhabibi.neo.model.*
import org.jetbrains.anko.coroutines.experimental.bg

/**
 * Created by mrhabibi on 10/26/17.
 */
object Matrix {

    lateinit var atman: Atman

    var toggles = HashMap<String, NeoToggle>()
    var configs = HashMap<String, NeoConfig>()
    var preferences = HashMap<String, NeoPreference>()
    var variables = HashMap<String, NeoVariable>()

    lateinit var getSingleToggle: ((NeoToggle) -> Unit, String) -> Unit
    lateinit var getSingleConfig: ((NeoConfig) -> Unit, String) -> Unit
    lateinit var getSinglePreference: ((NeoPreference) -> Unit, String) -> Unit
    lateinit var putSinglePreference: (String, Any) -> Unit

    fun <T : NeoModel> blend(map: HashMap<String, T>,
                             item: T) {
        val id = item.obtainId()
        val holderData = map.get(id)
        if (holderData == null) {
            map.put(id, item)
        } else {
            holderData.combineWith(item)
        }
    }

    fun <T : NeoModel> blend(map: HashMap<String, T>,
                             list: List<T>) {
        list.forEach {
            blend(map, it)
        }
    }

    fun <T : NeoModel> fetchData(holder: HashMap<String, T>,
                                 asset: List<T>,
                                 dao: NeoDao<T>,
                                 getMultiple: ((List<T>) -> Unit) -> Unit) {
        bg {
            blend(holder, asset)
            blend(holder, dao.get())

            getMultiple.invoke({ data ->
                blend(holder, data)
                dao.put(ArrayList(data))
            })
        }
    }

    fun <T : NeoModel> fetchData(id: String,
                                 holder: HashMap<String, T>,
                                 dao: NeoDao<T>,
                                 getSingle: ((T) -> Unit, String) -> Unit) {
        bg {
            getSingle.invoke({ data ->
                blend(holder, data)
                dao.put(data)
            }, id)
        }
    }

    fun initDatabase(context: Context) {
        atman = Room.databaseBuilder(context, Atman::class.java, "neo").build()
    }

    fun initToggle(assets: List<NeoToggle>, variables: List<NeoVariable>,
                   getSingle: ((NeoToggle) -> Unit, String) -> Unit,
                   getMultiple: ((List<NeoToggle>) -> Unit) -> Unit) {
        getSingleToggle = getSingle
        fetchData(
                holder = toggles,
                asset = assets,
                dao = atman.toggleDao(),
                getMultiple = getMultiple
        )
        variables.forEach { this.variables.put(it.key, it) }
    }

    fun initConfig(assets: List<NeoConfig>,
                   getSingle: ((NeoConfig) -> Unit, String) -> Unit,
                   getMultiple: ((List<NeoConfig>) -> Unit) -> Unit) {
        getSingleConfig = getSingle
        fetchData(
                holder = configs,
                asset = assets,
                dao = atman.configDao(),
                getMultiple = getMultiple
        )
    }

    fun initPreference(assets: List<NeoPreference>,
                       getSingle: ((NeoPreference) -> Unit, String) -> Unit,
                       putSingle: (String, Any) -> Unit,
                       getMultiple: ((List<NeoPreference>) -> Unit) -> Unit) {
        getSinglePreference = getSingle
        putSinglePreference = putSingle
        fetchData(
                holder = preferences,
                asset = assets,
                dao = atman.preferenceDao(),
                getMultiple = getMultiple
        )
    }

    fun getToggle(id: String): Boolean {
        fetchData(
                id = id,
                holder = toggles,
                dao = atman.toggleDao(),
                getSingle = getSingleToggle
        )
        val toggle = toggles.get(id)
        if (toggle == null) throw IllegalStateException(String.format("Have you registered toggle %s in Neo?", id))
        return toggle.isFeatureActivated(variables)
    }

    fun <T> getConfig(id: String): T {
        fetchData(
                id = id,
                holder = configs,
                dao = atman.configDao(),
                getSingle = getSingleConfig
        )
        val config = configs.get(id)
        if (config == null) throw IllegalStateException(String.format("Have you registered config %s in Neo?", id))
        return config.obj as T
    }

    fun <T> getPreference(id: String): T {
        fetchData(
                id = id,
                holder = preferences,
                dao = atman.preferenceDao(),
                getSingle = getSinglePreference
        )
        val preference = preferences.get(id)
        if (preference == null) throw IllegalStateException(String.format("Have you registered preference %s in Neo?", id))
        return preference.obj as T
    }

    fun setPreference(id: String, obj: Any) {
        putSinglePreference.invoke(id, obj)

        val preference = NeoPreference(id, obj)
        preferences.put(id, preference)
        bg { atman.preferenceDao().put(preference) }
    }

    fun clearPreference() {
        preferences.clear()
        bg { atman.preferenceDao().clear() }
    }

    @Deprecated("Will be deleted after hackathon")
    fun injectVariable(variable: NeoVariable) {
        variables.put(variable.key, variable)
    }

    @Deprecated("Will be deleted after hackathon")
    fun retrieveVariable(key: String): String {
        return variables.get(key)!!.value.invoke().toString()
    }
}