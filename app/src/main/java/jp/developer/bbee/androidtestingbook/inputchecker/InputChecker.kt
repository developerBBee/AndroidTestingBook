package jp.developer.bbee.androidtestingbook.inputchecker

import android.text.TextUtils

class InputChecker {
    fun isValid(text: String): Boolean {
        // android.* は実機やエミュレータ上で動作するので、モックにしないとテストできない
        // Robolectricライブラリを使うと、androidフレームワークのモックを作成できる
        if (TextUtils.isEmpty(text)) throw IllegalArgumentException("Cannot be blank")
        return text.length >= 3 && text.matches(Regex("[a-zA-Z0-9]+"))
    }
}