package jp.local.yukichan.mmsp2.notes.chords

import jp.local.yukichan.mmsp2.notes.Note
import jp.local.yukichan.mmsp2.notes.NoteManager
import jp.local.yukichan.mmsp2.notes.convertToNoteInfo
import jp.local.yukichan.mmsp2.notes.getAppendedNoteNo
import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import timber.log.Timber

/**
 * Created by takamk2 on 17/11/03.
 *
 * The Edit Fragment of Base Class.
 */
data class ChordOptions(val id: Int = 0) {

    var baseInterval = Intervals.Unison

    var tensions: Set<Intervals> = mutableSetOf()

    fun addTension(interval: Intervals) {
        if (interval.isTension) {
            tensions = tensions.plus(interval)
        }
    }

    fun removeTension(interval: Intervals) {
        if (interval.isTension) {
            tensions = tensions.minus(interval)
        }
    }

    fun getTensionNotes(noteManager: NoteManager, rootNote: Note): Set<Note> {
        val notes = mutableSetOf<Note>()
        Timber.d("DEBUG: getTensionNotes: tensions=$tensions")
        tensions.sortedBy { it.noteNo }.mapTo(notes) {
            val type = rootNote.noteInfo.type
            val noteNo = getAppendedNoteNo(rootNote.noteNo, it.noteNo)
            val noteInfo = convertToNoteInfo(noteNo, type)
            Timber.d("DEBUG: getTensionNotes: noteNo=$noteNo noteInfo=$noteInfo")
            if (noteInfo != null) {
                noteManager.get(noteInfo!!, rootNote.octave + 1)
            } else {
                noteManager.get(noteNo, rootNote.octave + 1)
            }
        }
        Timber.d("DEBUG: getTensionNotes: notes=$notes")
        return notes
    }

//    fun setBaseInterval(interval: Intervals) {
//        // TODO: セットできるかチェックする(Code+Tensionの構成音)
//        baseInterval = interval
//    }

    fun getBaseNote(noteManager: NoteManager, rootNote: Note): Note {
        val type = rootNote.noteInfo.type
        val noteNo = getAppendedNoteNo(rootNote.noteNo, baseInterval.noteNo)
        val noteInfo = convertToNoteInfo(noteNo, type)
        Timber.d("DEBUG: getTensionNotes: noteNo=$noteNo noteInfo=$noteInfo")
        return if (noteInfo != null) {
            noteManager.get(noteInfo!!, rootNote.octave - 1)
        } else {
            noteManager.get(noteNo, rootNote.octave - 1)
        }
    }

    fun getDisplayTensions(): String {
        val sb = StringBuffer()
        tensions.forEach {
            if (!sb.isEmpty()) {
                sb.append(", ")
            }
            sb.append(it.degreeTensionName)
        }
        if (!sb.isEmpty()) {
            sb.insert(0, "(")
            sb.append(")")
        }
        return sb.toString()
    }

    fun getDisplayBaseNote(rootNote: Note): String {
        if (baseInterval.noteNo == 0) {
            return ""
        }
        val sb = StringBuffer()
        val noteInfo = convertToNoteInfo(getAppendedNoteNo(rootNote.noteNo, baseInterval.noteNo), rootNote.noteInfo.type)
        if (noteInfo != null) {
            sb.append(noteInfo.displayName)
        }
        if (!sb.isEmpty()) {
            sb.insert(0, "on ")
        }
        return sb.toString()
    }
}