package jp.local.yukichan.mmsp2.extensions

import android.app.Activity
import android.content.Context
import jp.local.yukichan.mmsp2.application.CustomApplication

/**
 * Created by takamk2 on 17/10/17.
 *
 * The Edit Fragment of Base Class.
 */
fun Activity.getCustomApplication(): CustomApplication {
    return this.application as CustomApplication
}

fun Context.getCustomApplication(): CustomApplication {
    return this.applicationContext as CustomApplication
}
