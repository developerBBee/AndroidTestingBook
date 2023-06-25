package jp.developer.bbee.androidtestingbook.async

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class AsyncFetcherTest {
    lateinit var fetcher: StringFetcher
    lateinit var asyncFetcher: AsyncStringFetcher

    lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        fetcher = spy()
        asyncFetcher = AsyncStringFetcher(fetcher)

        latch = CountDownLatch(1)
    }

    /* コールバックの中でアサーションを行うのは避けたほうが良い
    @Test
    fun fetchAsync_callbackedProperly_onSuccess() {
        asyncFetcher.fetchAsync(
            onSuccess = { value ->
                assertThat(value).isEqualTo("foo")
                verify(fetcher, times(1)).fetch()
                println("success")
                latch.countDown()
            },
            onFailure = { _ ->
                println("Missing Test fetchAsync_callbackedProperly_onSuccess()")
            }
        )
        println("fetchAsync invoked.")
        latch.await() // latchが0になるまで待つ
    }

    @Test
    fun fetchAsync_callbackedProperly_onFailure() {
        doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()

        asyncFetcher.fetchAsync(
            onSuccess = { _ ->
                println("Missing Test fetchAsync_callbackedProperly_onFailure()")
            },
            onFailure = { error ->
                assertThat(error)
                    .isInstanceOf(RuntimeException::class.java)
                    .hasMessageContaining("ERROR")
                verify(fetcher, times(1)).fetch()
                println("failure")
                latch.countDown()
            }
        )
        println("fetchAsync invoked.")
        latch.await() // latchが0になるまで待つ
    }
     */

    // ExecutorServiceの非同期処理は、Futureを使うことで、
    // 非同期処理結果を待ち、テストスレッドで同期的に結果取得できる
    @Test
    fun fetchAsync_future_OK() {
        var actualValue: String? = null
        var actualError: Throwable? = null

        asyncFetcher.fetchAsync(
            onSuccess = { value -> actualValue = value },
            onFailure = { error -> actualError = error }
        ).get()

        verify(fetcher, times(1)).fetch()
        assertThat(actualValue).isEqualTo("foo")
        assertThat(actualError).isNull()
    }

    @Test
    fun fetchAsync_future_NG() {
        // fetcher.fetch()が例外を投げるようにスタブを設定
        doThrow(RuntimeException("ERROR")).whenever(fetcher).fetch()

        var actualValue: String? = null
        var actualError: Throwable? = null

        asyncFetcher.fetchAsync(
            onSuccess = { value -> actualValue = value },
            onFailure = { error -> actualError = error }
        ).get()

        verify(fetcher, times(1)).fetch()
        assertThat(actualValue).isNull()
        assertThat(actualError)
            .isInstanceOf(RuntimeException::class.java)
            .hasMessageContaining("ERROR")
    }
}