package jp.local.yukichan.mmsp2.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import jp.local.yukichan.mmsp2.R
import jp.local.yukichan.mmsp2.events.KeyboardEvent
import jp.local.yukichan.mmsp2.extensions.getCustomApplication
import jp.local.yukichan.mmsp2.notes.*
import jp.local.yukichan.mmsp2.notes.chords.getIntervalFromNoteNo
import jp.local.yukichan.mmsp2.notes.scale.NoteOfScale
import jp.local.yukichan.mmsp2.notes.scale.Scale
import jp.local.yukichan.mmsp2.sequence.SequenceItem
import kotlinx.android.synthetic.main.layout_keyboard.view.*
import timber.log.Timber

class KeyboardView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
    : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val TAG = KeyboardView::class.java.name

    private val keyViews: List<KeyView>

    private val eventObservable: Observable<KeyboardEvent>

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    private val LOGTAG: String = KeyboardView::class.java.simpleName

    private var octave: Int = -1

    private var noteManager: NoteManager

    init {
        if (attrs != null) {
            // get custom attributes
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.KeyboardView, 0, 0)
            octave = typedArray.getInt(R.styleable.KeyboardView_octave, -1)
        }

        noteManager = context.getCustomApplication().noteManager

        View.inflate(context, R.layout.layout_keyboard, this)
        keyViews = listOf<KeyView>(white1, white2, white3, white4, white5, white6, white7,
                black1, black2, black3, black4, black5)
        eventObservable = Observable
                .merge(keyViews.toObservable().map { it.getClickObservable() })
                .map { it.copy(octave = octave) }
                .publish()
                .autoConnect()
        context.getCustomApplication().eventManager.putKeyboardEventObservable(octave, eventObservable)

        val subject: Subject<KeyboardEvent> = PublishSubject.create()
        eventObservable.subscribe { subject.onNext(it) }
        subject.filter { it.type == KeyboardEvent.Type.Click }
                .map { noteManager.get(it.noteNo, it.octave) }
                .subscribe {
                    play(it)
                }
    }

    fun updateView(seqItem: SequenceItem) {
        Timber.d("updateview: seqItem.notes=${seqItem.notes}. octave=$octave")
        // clear all
        keyViews.forEach { it.updateView(false) }
        keyViews.filter { seqItem.notes.contains(noteManager.get(it.noteNo, octave)) }
                .forEach {
                    it.updateView(true)
                }
    }

    fun updateView(selectedNotes: SelectedNotes) {
        Timber.d("updateview: selectedNotes=$selectedNotes octave=$octave")
        // clear all
        keyViews.forEach { it.updateView(false) }
        keyViews.filter { selectedNotes.contains(noteManager.get(it.noteNo, octave)) }
                .forEach {
                    it.updateView(true)
                }
    }

    fun updateInfo(seqItem: SequenceItem?) {
        keyViews.forEach {
            it.updateInfoText(null)
//            it.updateNoteText(null)
        }
        if (seqItem == null) return

        val intervalNoteNoSet = seqItem.getIntervalsSetOfChord()
        Timber.i("updateInfo: intervalNoteNoSet=$intervalNoteNoSet")

        val codeIntervalSet = seqItem.chord!!.notes.map { getIntervalFromNoteNo(it.noteNo) }

        keyViews.forEach { keyView ->
            val intervals = intervalNoteNoSet.find { getAppendedNoteNo(it.noteNo, seqItem.chord!!.rootNote.noteNo) ==  keyView.noteNo}
            Timber.i("updateInfo: intervals=$intervals")
            if (intervals != null) {
                val degree = if (codeIntervalSet.contains(intervals)) {
                    intervals.degreeName
                } else {
                    intervals.degreeTensionName
                }
                keyView.updateInfoText(degree)
            }
        }
    }

    fun updateInfo(scale: Scale?) {
        keyViews.forEach {
            it.updateInfoText(null)
            it.updateNoteText(null)
        }

        if (scale != null) {
            val intervalNoteNoSet = scale.getIntervalNoteNoSet()
            scale.notes
            Timber.d("updateInfoText: scale=$scale intervalNoteNoSet=$intervalNoteNoSet")
            keyViews.filter { scale.notes.map { it.noteNo }.contains(it.noteNo) }
                    .map { keyView ->
                        val note = scale.notes.find { it.noteNo == keyView.noteNo }
                        mapOf("keyView" to keyView, "note" to note)
                    }
                    .forEach {
                        val noteOfScale = it["note"] as NoteOfScale?
                        val info: String = noteOfScale?.degreeName ?: "-"
                        (it["keyView"] as KeyView).updateInfoText(info)
                    }

            keyViews.forEach {
                val noteInfo = convertToNoteInfo(it.noteNo, scale.type)
                val note = if (noteInfo != null) {
                    noteManager.get(noteInfo, 2)
                } else {
                    noteManager.get(it.noteNo, 2)
                }
                it.updateNoteText(note.displayName)
            }
        }
    }

    private fun play(note: Note?) {
        note.let {
            val soundManager = context.getCustomApplication().soundManager
            soundManager.play(note)
        }
    }
}

