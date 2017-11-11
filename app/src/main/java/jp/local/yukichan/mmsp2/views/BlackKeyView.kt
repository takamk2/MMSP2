package jp.local.yukichan.mmsp2.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import jp.local.yukichan.mmsp2.R
import jp.local.yukichan.mmsp2.events.KeyboardEvent
import jp.local.yukichan.mmsp2.events.KeyboardEvent.Type.*
import jp.local.yukichan.mmsp2.notes.Note
import kotlinx.android.synthetic.main.parts_black_key.view.*
import timber.log.Timber

class BlackKeyView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
    : BaseKeyView(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    private val clickObservable: Observable<KeyboardEvent>

    init {
        View.inflate(context, R.layout.parts_black_key, this)
        clickObservable = Observable.merge(
                RxView.clicks(keyArea).map {
                    KeyboardEvent(name!!, Click, noteNo, 0)
                },
                RxView.longClicks(keyArea).map {
                    KeyboardEvent(name!!, LongClick, noteNo, 0)
                })

        val note = Note(noteNo)
        noteText.text = note.displayName
    }

    override fun updateView(isSelected: Boolean) {
        Log.d(javaClass.simpleName, "updateView($isSelected): name=$name")
        if (isSelected) {
            bgImage.setColorFilter(
                    context.resources.getColor(R.color.whiteKeySelectedMaskForBlack, context.theme))
            noteText.setTextColor(context.resources.getColor(R.color.textKeyboardInfoAccentForBlack, context.theme))
            infoText.setTextColor(context.resources.getColor(R.color.textKeyboardInfoAccentForBlack, context.theme))
        } else {
            bgImage.colorFilter = null
            noteText.setTextColor(context.resources.getColor(R.color.textKeyboardInfoForBlack, context.theme))
            infoText.setTextColor(context.resources.getColor(R.color.textKeyboardInfoForBlack, context.theme))
        }
    }

    override fun updateNoteText(value: String?) {
        Timber.d("updateNoteText(value=$value) name=$name")
        if (value != null) {
            noteText.text = value
        } else {
            noteText.text = null
        }
    }

    override fun updateInfoText(value: String?) {
        Timber.d("updateInfoText(value=$value) name=$name")
        if (value != null) {
            infoText.text = value
        } else {
            infoText.text = null
        }
    }

    override fun getClickObservable(): Observable<KeyboardEvent> {
        return clickObservable
    }
}
