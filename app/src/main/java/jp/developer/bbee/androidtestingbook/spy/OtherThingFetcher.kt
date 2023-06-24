package jp.developer.bbee.androidtestingbook.spy

// スタブとするためにTargetClassから切り離した
open class OtherThingFetcher {
    open fun fetchOtherThing(): String {
        // implementation
        return "otherThing"
    }
}