package jp.developer.bbee.androidtestingbook.async

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AsyncStringFetcher(
    val fetcher: StringFetcher
) {
    val executor: ExecutorService = Executors.newCachedThreadPool()

    fun fetchAsync(
        onSuccess: (value: String) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        executor.submit {
            try {
                val value = fetcher.fetch()
                onSuccess(value)
            } catch (error: Throwable) {
                onFailure(error)
            }
        }
    }
}