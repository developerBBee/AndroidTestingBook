package jp.developer.bbee.androidtestingbook.weathercast

open class WeatherFormatter {
    open fun format(weather: Weather): String = "Weather is ${weather}"
}

class SpyWeatherFormatter: WeatherFormatter() {
    var weather: Weather? = null
    var isCalled = false

    override fun format(weather: Weather): String {
        this.weather = weather
        isCalled = true
        return super.format(weather)
    }
}