package jp.developer.bbee.androidtestingbook.weathercast

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test


class WeatherForecastTest {
    private lateinit var satellite: Satellite
    private lateinit var recorder: WeatherRecorder
    private lateinit var weatherForecast: WeatherForecast

    @Before
    fun setUp() {
        satellite = mock(name = "MockSatellite")
        whenever(satellite.getWeather(any(), any()))
            .thenReturn(Weather.RAINY)
        whenever(satellite.getWeather(eq(1.0), any()))
            .thenReturn(Weather.CLOUDY)
        whenever(satellite.getWeather(1.0, 1.0))
            .thenReturn(Weather.SUNNY)

        recorder = mock(name = "MockRecorder")

        weatherForecast = WeatherForecast(satellite, recorder)
    }

    @Test
    fun argumentCapture_Rainy() {
        weatherForecast.recordCurrentWeather(35.6692, 139.7614)
        weatherForecast.recordCurrentWeather(1.0, 139.7614)
        weatherForecast.recordCurrentWeather(1.0, 1.0)
        weatherForecast.recordCurrentWeather(35.6692, 1.0)

        argumentCaptor<Record>().apply {
            verify(recorder, times(4)).record(capture())
            // firstValueはRecord型, allValues[index]で(index+1)番目にキャプチャした値を取得
            assertThat(firstValue.description).isEqualTo("Weather is RAINY")
            assertThat(secondValue.description).isEqualTo("Weather is CLOUDY")
            assertThat(thirdValue.description).isEqualTo("Weather is SUNNY")
            assertThat(allValues[3].description).isEqualTo("Weather is RAINY")
        }
    }
}