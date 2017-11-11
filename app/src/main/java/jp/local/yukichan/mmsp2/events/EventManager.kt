package jp.local.yukichan.mmsp2.events

import android.util.Log
import android.view.KeyEvent
import io.reactivex.Observable

/**
 * Created by takamk2 on 17/10/15.
 *
 * The Edit Fragment of Base Class.
 */
class EventManager {

    private val LOGTAG: String = EventManager::class.java.simpleName

    private val keyboardEventObservable = mutableMapOf<Int, Observable<KeyboardEvent>>()

    fun putKeyboardEventObservable(id: Int, observable: Observable<KeyboardEvent>) {
        Log.d(LOGTAG, "putKeyboardEventObservable(id=$id, observable=$observable")
        if (keyboardEventObservable.containsKey(id)) {
            // TODO: Observableが変わったことを通知しないと受け取れないかも
        }
        keyboardEventObservable.put(id, observable)
    }

    fun getKeyboardEventObservable(id: Int): Observable<KeyboardEvent>? {
        return keyboardEventObservable[id]
    }

    fun getAllKeyboardEventObservable(): Observable<KeyboardEvent>? {
        return Observable.merge(keyboardEventObservable[0], keyboardEventObservable[1],
                keyboardEventObservable[2], keyboardEventObservable[3])
    }
}