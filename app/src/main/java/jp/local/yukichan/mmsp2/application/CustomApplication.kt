package jp.local.yukichan.mmsp2.application

import android.app.Application
import jp.local.yukichan.mmsp2.events.EventManager
import jp.local.yukichan.mmsp2.notes.NoteManager
import jp.local.yukichan.mmsp2.notes.chords.ChordManager
import jp.local.yukichan.mmsp2.notes.scale.ScaleManager
import jp.local.yukichan.mmsp2.sounds.SoundManager
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
class CustomApplication : Application() {

    lateinit var eventManager: EventManager
    lateinit var noteManager: NoteManager
    lateinit var scaleManager: ScaleManager
    lateinit var codeManager: ChordManager
    lateinit var soundManager: SoundManager

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        eventManager = EventManager()
        noteManager = NoteManager()
        scaleManager = ScaleManager(noteManager)
        codeManager = ChordManager(noteManager)
        soundManager = SoundManager(this, noteManager)
    }
}