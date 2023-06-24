package jp.developer.bbee.androidtestingbook.inputchecker

import android.os.Build.VERSION_CODES.*
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.IllegalArgumentException

@Config(minSdk = 24, maxSdk = 33)
@RunWith(RobolectricTestRunner::class)
class InputCheckerTest {
    @Test
    fun isValid_givenBlank_throwIllegalArgumentException() {
        val sut = InputChecker()
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy { sut.isValid("") }
            .withMessage("Cannot be blank")
            .withNoCause()
    }
}