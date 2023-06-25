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

    /*
    @Test
    fun fetchAsync_expectFailButPass_01() {
        asyncFetcher.fetchAsync(
            onSuccess = { _ -> fail("onSuccess")},
            onFailure = { _ -> fail("onFailure")}
        ) // fail()を渡して失敗するようにしても非同期スレッドを待たずに終了するため成功する
    }

    @Test
    fun fetchAsync_expectFailButPass_02() {
        asyncFetcher.fetchAsync(
            onSuccess = { _ -> fail("onSuccess")},
            onFailure = { _ -> fail("onFailure")}
        )
        Thread.sleep(2000L) // 待ってもダメ
    }

    @Test(expected = RuntimeException::class)
    fun fetchAsync_expectPassButFail() {
        asyncFetcher.fetchAsync(
            onSuccess = { _ -> throw RuntimeException("onSuccess") },
            onFailure = { _ -> throw RuntimeException("onFailure") }
        ) // RuntimeExceptionをthrowしても、非同期スレッドの例外を検知できないため失敗する
        Thread.sleep(2000L)
    }
     */

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
}