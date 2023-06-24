package jp.developer.bbee.androidtestingbook.weathercast

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test


class WeatherForecastTest {
    private lateinit var weatherForecast: WeatherForecast
    private lateinit var formatter: WeatherFormatter

    @Before
    fun setUp() {
        formatter = spy(WeatherFormatter()) // mockito による spy の作成
        weatherForecast = WeatherForecast(formatter = formatter)
    }

    @Test
    fun recordCurrentWeather_assertFormatterCalled() {
        weatherForecast.recordCurrentWeather(37.580006, 139.239418)
        verify(formatter, times(1)).format(any()) // formatter.formet() が呼ばれたことを確認
    }
}