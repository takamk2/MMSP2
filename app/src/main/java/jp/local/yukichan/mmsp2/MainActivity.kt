package jp.local.yukichan.mmsp2

import android.content.Context
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import jp.local.yukichan.mmsp2.events.KeyboardEvent
import jp.local.yukichan.mmsp2.extensions.getCustomApplication
import jp.local.yukichan.mmsp2.notes.chords.*
import jp.local.yukichan.mmsp2.notes.scale.Scale
import jp.local.yukichan.mmsp2.notes.scale.ScaleManager
import jp.local.yukichan.mmsp2.notes.scale.SelectedScale
import kotlinx.android.synthetic.main.activity_main.*
import jp.local.yukichan.mmsp2.notes.*
import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.sequence.SequenceItem
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private val LOGTAG: String = MainActivity::class.java.simpleName

    private lateinit var noteManager: NoteManager
    private lateinit var scaleManager: ScaleManager
    private lateinit var codeManager: ChordManager

//    private lateinit var selectedNotes: SelectedNotes
//    private lateinit var selectedScale: SelectedScale
//    private lateinit var selectedCode: SelectedChord

    private lateinit var scaleListAdapter: ArrayAdapter<Scale>

    private lateinit var chordListAdapter: ArrayAdapter<Chord>

    private lateinit var seqItem: SequenceItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteManager = getCustomApplication().noteManager
        scaleManager = getCustomApplication().scaleManager
        codeManager = getCustomApplication().codeManager
//        selectedNotes = SelectedNotes(1)
//        selectedScale = SelectedScale(1)
//        selectedCode = SelectedChord(1)

        seqItem = SequenceItem(noteManager, 0)

        val chordOptions = ChordOptions()
        seqItem.chordOptions = chordOptions
        seqItem.chordOptions!!.baseInterval = Intervals.P5

        chordOptions.addTension(Intervals.M2)
        chordOptions.addTension(Intervals.aug4)

        val scales = scaleManager.getCandidateScales(setOf(0, 2))

        // TODO: DEBUG
        seqItem.scale = scaleManager.getScale()
        selectedScaleText.text = seqItem.scale.toString()
        RxView.clicks(selectedScaleText).subscribe {
            seqItem.scale!!.play(getCustomApplication().soundManager)
        }

        // TODO: DEBUG getCandidateCodes
        val codes = codeManager.getCandidateCodes(NoteInfo.C)

        // TODO: DEBUG
        seqItem.chord = codes[0]
        selectedCodeText.text = seqItem.getDisplayCodeName()
        RxView.clicks(selectedCodeText).subscribe {
            seqItem.play(getCustomApplication().soundManager)
        }

        chordListAdapter = ArrayAdapter<Chord>(this, android.R.layout.simple_list_item_1, codes)
        chordList.adapter = chordListAdapter
        chordList.setOnItemClickListener{ _: AdapterView<*>, _: View, position : Int, _: Long ->
            val code = codes[position]
            code.play(getCustomApplication().soundManager)
            seqItem.chord = code
            selectedCodeText.text = seqItem.getDisplayCodeName()
            keyboard1.updateInfo(seqItem)
            keyboard2.updateInfo(seqItem)
            keyboard3.updateInfo(seqItem)
            keyboard4.updateInfo(seqItem)
        }

        scaleListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, scales)
        scaleList.adapter = scaleListAdapter
        scaleList.setOnItemClickListener { adapterView: AdapterView<*>, _: View,
                                           position : Int, _: Long ->
            val scale = adapterView.getItemAtPosition(position) as Scale
            seqItem.scale = scale
            selectedScaleText.text = seqItem.toString()

//            keyboard1.updateInfo(seqItem.scale)
//            keyboard2.updateInfo(seqItem.scale)
//            keyboard3.updateInfo(seqItem.scale)
//            keyboard4.updateInfo(seqItem.scale)

            // TODO: getCo
            val candidateCodes = codeManager.getCandidateCodes(seqItem.scale!!.rootNote.noteInfo)

            chordListAdapter.let {
                it.clear()
                it.addAll(candidateCodes)
                it.notifyDataSetChanged()
            }

            keyboard1.updateInfo(seqItem.scale)
            keyboard2.updateInfo(seqItem.scale)
            keyboard3.updateInfo(seqItem.scale)
            keyboard4.updateInfo(seqItem.scale)
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val onAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener {
            Timber.d("DEBUG: audioFocusChanged!! val=$it")
        }
        RxView.clicks(focusRequestButton).subscribe {
            Timber.d("DEBUG: request focus")
            audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
        }

        RxView.clicks(focusAbandonButton).subscribe {
            Timber.d("DEBUG: abandon focus")
            audioManager.abandonAudioFocus(onAudioFocusChangeListener)
        }
    }

    private var disposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
        disposable = getCustomApplication().eventManager
                .getAllKeyboardEventObservable()
                ?.filter { it.type == KeyboardEvent.Type.LongClick }
                ?.subscribe {
                    it.let {
                        Log.d(LOGTAG, it.toString())
                        val note = noteManager.get(it)
                        if (!seqItem.notes.contains(note)) {
                            seqItem.addNote(note)
                        } else {
                            seqItem.removeNote(note)
                        }
                        keyboard1.updateView(seqItem)
                        keyboard2.updateView(seqItem)
                        keyboard3.updateView(seqItem)
                        keyboard4.updateView(seqItem)

                        val scales = scaleManager.getCandidateScales(seqItem)
                        scale.text = scales.toString()

                        scaleListAdapter.let {
                            it.clear()
                            it.addAll(scales)
                            it.notifyDataSetChanged()
                        }

                        notes.text = seqItem.notes.map { it.noteNo }.toList().sorted().toString()
                    }
                }
    }

    override fun onPause() {
        super.onPause()
        if (disposable != null) {
            if (!disposable!!.isDisposed) {
                disposable!!.dispose()
            }
            disposable = null
        }
    }
}
