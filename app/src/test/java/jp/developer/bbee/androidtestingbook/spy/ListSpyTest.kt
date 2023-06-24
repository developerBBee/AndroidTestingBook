package jp.developer.bbee.androidtestingbook.spy

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.*
import org.junit.Test
import kotlin.RuntimeException

class ListSpyTest {
    @Test
    fun spyList_returnHello() {
        val list = mutableListOf<String>()
        val spyList = spy(list)

        /* スタブメソッドが呼び出されるより先に、実インスタンスである空の
         * ArrayList の先頭要素にアクセスしようとして例外が発生してしまう
        whenever(spyList[any()]).thenReturn("HELLO")
        assertThat(spyList[0]).isEqualTo("HELLO") // spyList[0] -> IndexOutOfBoundsException
         */

        // 前置記法で例外を回避
        // doReturn
        doReturn("HELLO").whenever(spyList)[any()]
        assertThat(spyList[0]).isEqualTo("HELLO")

        // doThrow
        doThrow(RuntimeException("ERROR")).whenever(spyList).indexOf(any())
        assertThatExceptionOfType(RuntimeException::class.java).isThrownBy {
            spyList.indexOf("HELLO")
        }.withMessage("ERROR").withNoCause()

        // doNothing
        doNothing().whenever(spyList).clear() // 何もしないことの明示？
        spyList.clear() // モックは何もしないようになっているので、クリアされない
        println("Return ${spyList[0]}")

        doAnswer {
            val index = it.arguments[0] as Int
            return@doAnswer if (index == 0) "HELLO" else "WORLD"
        }.whenever(spyList)[any()]
        assertThat(spyList[0]).isEqualTo("HELLO")
        assertThat(spyList[1]).isEqualTo("WORLD")
    }
}