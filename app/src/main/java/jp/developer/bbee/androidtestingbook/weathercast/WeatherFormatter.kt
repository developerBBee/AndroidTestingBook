package jp.developer.bbee.androidtestingbook.weathercast

open class WeatherFormatter {
    open fun format(weather: Weather): String = "Weather is ${weather}"
}