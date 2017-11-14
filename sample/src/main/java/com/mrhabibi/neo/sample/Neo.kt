package com.mrhabibi.neo.sample

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.mrhabibi.neo.Matrix
import com.mrhabibi.neo.model.NeoConfig
import com.mrhabibi.neo.model.NeoPreference
import com.mrhabibi.neo.model.NeoToggle
import com.mrhabibi.neo.model.NeoVariable
import com.mrhabibi.neo.validator.DateValidator
import com.mrhabibi.neo.validator.DiscreteValidator
import java.io.Serializable
import java.util.*

/**
 * Created by mrhabibi on 14/11/17.
 *
 * Helper to connect Neo and the Matrix
 */
object Neo {

    // Utils

    /**
     * Called inside onCreate of Application class
     */
    fun init(context: Context) {
        Matrix.initDatabase(context)
        Matrix.initToggle(
                assets = populateToggles(),
                variables = populateVariables(),
                getSingle = { handler, id ->
                    // handler.invoke(NeoToggle(data.id, data.active, data.segmentation))
                },
                getMultiple = { handler ->
                    // handler.invoke(list.map { data ->
                    //    NeoToggle(data.id, data.active, data.segmentation)
                    // }.toList())
                }
        )
        Matrix.initConfig(
                assets = populateConfigs(),
                getSingle = { handler, id ->
                    // handler.invoke(NeoConfig(data.id, data.data))
                },
                getMultiple = { handler ->
                    // handler.invoke(list.map { data ->
                    //     NeoConfig(data.id, data.data)
                    // }.toList())
                }
        )

        initPreference()
    }

    /**
     * Called also when user has been logged in
     */
    fun initPreference() {
        Matrix.initPreference(
                assets = populatePreferences(),
                getSingle = { handler, id ->
                    // handler.invoke(NeoPreference(data.id, data.data))
                },
                getMultiple = { handler ->
                    // handler.invoke(list.map { data ->
                    //    NeoPreference(data.id, data.data)
                    // }.toList())
                },
                putSingle = { id, obj ->
                    // Send obj to your server
                }
        )
    }

    /**
     * Called when user has been logged out
     */
    fun clearPreference() {
        Matrix.clearPreference()
    }

    // Assets

    fun populateVariables(): List<NeoVariable> = listOf(
            NeoVariable("date", { DateValidator.sdf.format(Date()) }, DateValidator()),
            NeoVariable("is-login", { true }, DiscreteValidator())
    )

    fun populateToggles(): List<NeoToggle> = listOf(
            NeoToggle("flight-shown", false),
            NeoToggle("nego-shown", true, mapOf(
                    Pair("is-login", "false"))
            ),
            NeoToggle("harbolnas-shown", true, mapOf(
                    Pair("is-login", "false"),
                    Pair("date", "10/12/2017 - 20/12/2017"))
            )
    )

    fun populateConfigs(): List<NeoConfig> = listOf(
            NeoConfig("flight", FlightConfig()),
            NeoConfig("harbolnas", HarbolnasConfig())
    )

    fun populatePreferences(): List<NeoPreference> = listOf(
            NeoPreference("bpjs", BpjsPreference())
    )

    // Getters

    fun isFlightShown(): Boolean = Matrix.getToggle("flight-shown")

    fun isNegoShown(): Boolean = Matrix.getToggle("nego-shown")

    fun isHarbolnasShown(): Boolean = Matrix.getToggle("harbolnas-shown")

    fun getFlightConfig(): FlightConfig = Matrix.getConfig("flight")

    fun getHarbolnasConfig(): HarbolnasConfig = Matrix.getConfig("harbolnas")

    fun getBpjsPreference(): BpjsPreference = Matrix.getPreference("bpjs")

    fun setBpjsPreference(obj: BpjsPreference) = Matrix.setPreference("bpjs", obj)

    // Classes

    class FlightConfig : Serializable {
        @SerializedName("booking-time")
        var bookingTime: Long = -1

        @SerializedName("warning-message")
        var warningMessage: String = "[Nothing]"
    }

    class HarbolnasConfig : Serializable {
        @SerializedName("info")
        var info: String = "[Nothing]"

        @SerializedName("voucher-usage")
        var voucherUsage: Int = -1
    }

    class BpjsPreference : Serializable {
        @SerializedName("number")
        var number: String = "[Nothing]"
    }
}