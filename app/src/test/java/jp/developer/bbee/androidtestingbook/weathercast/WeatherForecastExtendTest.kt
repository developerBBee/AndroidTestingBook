package jp.developer.bbee.androidtestingbook.weathercast

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test

class WeatherForecastExtendTest {
    private lateinit var satellite: Satellite
    private lateinit var weatherForecast: WeatherForecast

    // 宣言的なスタブ
    @Before
    fun setUp() {
        satellite = mock(name = "MockSatellite") {
            // onスコープ内でスタブで置き換えるメソッドを指定し、doReturnで返り値を指定する
            on { getWeather(any(), any()) } doReturn Weather.SUNNY
            on { getWeather(eq(35.0), eq(140.0)) } doReturn Weather.CLOUDY
            on { getWeather(eq(35.0), eq(141.0)) } doReturn Weather.RAINY
            on { getWeather(eq(35.0), eq(142.0)) } doThrow RuntimeException("Stub")
        }

        weatherForecast = WeatherForecast(satellite)
    }

    @Test
    fun shouldBringUmbrella_Sunny() {
        assertThat(weatherForecast.shouldBringUmbrella(35.0, 135.0)).isFalse
    }

    @Test
    fun shouldBringUmbrella_Cloudy() {
        assertThat(weatherForecast.shouldBringUmbrella(35.0, 140.0)).isFalse
    }

    @Test
    fun shouldBringUmbrella_Rainy() {
        assertThat(weatherForecast.shouldBringUmbrella(35.0, 141.0)).isTrue
    }

    @Test
    fun shouldBringUmbrella_Exception() {
        assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy {
                weatherForecast.shouldBringUmbrella(35.0, 142.0)
            }
            .withMessage("Stub")
            .withNoCause()
    }
}