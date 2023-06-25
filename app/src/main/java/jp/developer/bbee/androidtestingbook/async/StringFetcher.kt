package jp.developer.bbee.androidtestingbook.async

open class StringFetcher {
    open fun fetch(): String {
        Thread.sleep(1000L) // Instead of long processing
        return "foo"
    }
}