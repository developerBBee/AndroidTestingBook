package jp.developer.bbee.androidtestingbook.weathercast

import kotlin.random.Random

open class Satellite {
    open fun getWeather(
        latitude: Double,
        longitude: Double
    ): Weather {
        val r = Random.nextInt(10)
        return when (r) {
            in 0..5 -> Weather.SUNNY
            in 6..8 -> Weather.CLOUDY
            else -> Weather.RAINY
        }
    }
}