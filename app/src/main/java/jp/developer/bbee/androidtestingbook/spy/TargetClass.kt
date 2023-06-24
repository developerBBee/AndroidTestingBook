package jp.developer.bbee.androidtestingbook.spy

class TargetClass(
    private val fetcher: OtherThingFetcher,
    private val handler: OtherThingHandler
) {
    fun doSomething() {
        val text = fetcher.fetchOtherThing()
        handler.doWithOtherThing(text)
    }
}