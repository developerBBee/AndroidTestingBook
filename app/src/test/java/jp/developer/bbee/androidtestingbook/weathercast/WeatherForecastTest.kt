package jp.developer.bbee.androidtestingbook.weathercast

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness


class WeatherForecastTest {
    @get:Rule
    val rule = MockitoJUnit.rule().strictness(Strictness.WARN) // WARNレベルでモックの使い方をチェック
    @Mock
    private lateinit var sattelite: Satellite
    @Mock
    private lateinit var recorder: WeatherRecorder
    @Spy
    val formatter: WeatherFormatter = WeatherFormatter() // spyは実インスタンスを指定する

    private lateinit var weatherForecast: WeatherForecast

    @Before
    fun setUp() {
        /* Ruleで初期化するので不要
        MockitoAnnotations.initMocks(this)
         */
        whenever(sattelite.getWeather(any(), any())).thenReturn(Weather.SUNNY)
        weatherForecast = WeatherForecast(sattelite, recorder, formatter)
    }

    @Test
    fun shouldBringUmbrella_WeatherSunny_ReturnsFalse() {
        assertThat(weatherForecast.shouldBringUmbrella(37.580006, 139.239418))
            .isFalse()
    }

    @Test
    fun recordCurrentWeather_assertFormatterCalled() {
        weatherForecast.recordCurrentWeather(37.580006, 139.239418)
        verify(formatter, times(1)).format(any()) // formatter.formet() が呼ばれたことを確認
    }
}