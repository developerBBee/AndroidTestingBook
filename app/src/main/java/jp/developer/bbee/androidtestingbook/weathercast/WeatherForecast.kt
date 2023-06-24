package jp.developer.bbee.androidtestingbook.weathercast

class WeatherForecast (
    val satellite: Satellite = Satellite(),
    val recorder: WeatherRecorder = WeatherRecorder(),
    val formatter: WeatherFormatter = WeatherFormatter()
) {
    fun shouldBringUmbrella(
        latitude: Double,
        longitude: Double
    ): Boolean {
        val weather = satellite.getWeather(latitude, longitude)
        return when (weather) {
            Weather.SUNNY, Weather.CLOUDY -> false
            Weather.RAINY -> true
        }
    }

    fun recordCurrentWeather(
        latitude: Double,
        longitude: Double
    ) {
        val weather = satellite.getWeather(latitude, longitude)
        val description = formatter.format(weather)
        recorder.record(Record(description))
    }
}