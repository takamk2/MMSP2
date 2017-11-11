package jp.local.yukichan.mmsp2.notes.chords

import jp.local.yukichan.mmsp2.notes.*
import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.sounds.SoundManager
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/19.
 *
 * The Edit Fragment of Base Class.
 */
data class Chord(private val noteManager: NoteManager,
                 val rootNote: Note,
                 val constitution: ChordConstitution) {

    val type = rootNote.noteInfo.type
    val notes = mutableListOf<Note>()

    init {
        constitution.intervalSet.mapTo(notes) {
            val noteNo = getAppendedNoteNo(rootNote.noteNo, it.noteNo)
            val noteInfo = convertToNoteInfo(noteNo, type)
            Timber.d("init: noteNo=$noteNo noteInfo=$noteInfo")
            if (noteInfo != null) {
                noteManager.get(noteInfo!!, rootNote.octave)
            } else {
                noteManager.get(noteNo, rootNote.octave)
            }
        }
    }

    override fun toString(): String {
        return rootNote.displayName + constitution.dispName
    }

    fun play(soundManager: SoundManager) {
//        notes.add(noteManager.get(rootNote.noteInfo, rootNote.octave - 1))
        notes.forEachIndexed { index, note ->
            val convertedNote = if (note.noteInfo.type == NoteInfo.Type.Flat) {
                noteManager.get(convertToNoteInfo(note.noteNo)!!, note.octave)
            } else {
                note
            }
            if (index == 0) {
                soundManager.play(convertedNote, 1f, 0.6f)
            } else {
                soundManager.play(convertedNote, 0.8f, 0.8f)
            }
        }
    }

    fun getIntervalsSet(): Set<Intervals> {
        return notes.map {
            getIntervalFromNoteNo(getAppendedNoteNo(rootNote.noteNo, it.noteNo))
        }.toSet()
    }
}
