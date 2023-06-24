package jp.developer.bbee.androidtestingbook.jetpack

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.developer.bbee.androidtestingbook.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment

// Deprecated
@RunWith(AndroidJUnit4::class)
class JetpackTest {
    @Test
    fun gettingContextTest() {
        val context = InstrumentationRegistry.getTargetContext()
        val appName = context.getString(R.string.app_name)
        assertThat(appName).isEqualTo("AndroidTestingBook")
    }
}

// 非推奨を推奨版に書き換える
@RunWith(AndroidJUnit4::class)
class JetpackNewTest {
    @Test
    fun gettingContextTest() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val appName = context.getString(R.string.app_name)
        assertThat(appName).isEqualTo("AndroidTestingBook")
    }
}