package jp.local.yukichan.mmsp2.notes

import jp.local.yukichan.mmsp2.events.KeyboardEvent
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
class NoteManager {

    val notes = mutableListOf<Note>()

    fun get(noteInfo: NoteInfo, octave: Int): Note {
        val note = Note(noteInfo, octave)
        Timber.d("DEBUG: get: note=$note")
        notes.add(note)
        return note
    }

    fun get(noteIdInInterval: Int, octave: Int): Note {
        // TODO: noteIdInIntervalがIntervalを超えた場合、octave繰り上げにする？
        var index = indexOf(noteIdInInterval, octave)
        if (index != -1) {
            return notes.get(index)
        }
        var interval = noteIdInInterval
        var oct = octave
        if (interval >= NoteConstants.INTERVAL) {
            interval %= NoteConstants.INTERVAL
            oct += noteIdInInterval / NoteConstants.INTERVAL
        }
        val note = Note(convertToNoteInfo(interval)!!, oct)
        notes.add(note)
        return note
    }

    fun get(keyboardEvent: KeyboardEvent): Note {
        return get(keyboardEvent.noteNo, keyboardEvent.octave)
    }

    fun getIntervalNoteNoSet(): Set<Int> {
        return notes.map { it.noteNo }.toSet()
    }

    private fun indexOf(noteIdInInterval: Int, octave: Int): Int {
        notes.forEachIndexed { index, note ->
            if (note.sameAs(noteIdInInterval, octave)) {
                return index
            }
        }
        return -1
    }
}