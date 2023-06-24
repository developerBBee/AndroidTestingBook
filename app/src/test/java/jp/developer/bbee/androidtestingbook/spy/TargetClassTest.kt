package jp.developer.bbee.androidtestingbook.spy

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

class TargetClassTest {
    @Test
    fun testDoSomething() {
        val fethcer: OtherThingFetcher = mock()
        whenever(fethcer.fetchOtherThing()).thenReturn("OTHER")

        val handler: OtherThingHandler = mock()

        val target = TargetClass(fethcer, handler)
        target.doSomething()

        verify(handler, times(1)).doWithOtherThing(eq("OTHER"))
    }
}