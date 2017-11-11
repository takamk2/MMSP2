package jp.local.yukichan.mmsp2.views

import io.reactivex.Observable
import jp.local.yukichan.mmsp2.events.KeyboardEvent

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
interface KeyView {
    val noteNo: Int
    fun getClickObservable(): Observable<KeyboardEvent>
    fun updateView(isSelected: Boolean)
    fun updateNoteText(value: String?)
    fun updateInfoText(value: String?)
}