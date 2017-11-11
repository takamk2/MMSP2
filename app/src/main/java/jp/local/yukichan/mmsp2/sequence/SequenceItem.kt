package jp.local.yukichan.mmsp2.sequence

import jp.local.yukichan.mmsp2.notes.*
import jp.local.yukichan.mmsp2.notes.chords.Chord
import jp.local.yukichan.mmsp2.notes.chords.ChordOptions
import jp.local.yukichan.mmsp2.notes.chords.getIntervalFromNoteNo
import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.notes.scale.Scale
import jp.local.yukichan.mmsp2.sounds.SoundManager
import timber.log.Timber

/**
 * Created by takamk2 on 17/11/03.
 *
 * The Edit Fragment of Base Class.
 */
data class SequenceItem(val noteManager: NoteManager, val id: Int = 0) {
    var sortNo = 0
    var scale: Scale? = null
    var chord: Chord? = null
    var chordOptions: ChordOptions? = null
    var notes: Set<Note> = mutableSetOf()

    fun addNote(note: Note) {
        notes = notes.plus(note)
    }

    fun removeNote(note: Note) {
        notes = notes.minus(note)
    }

//    fun createSequenceItem(id: Int): SequenceItem {
//        // TODO: ここで今日のIDの割り振りを行う
//    }

    fun getDisplayCodeName(): String {
        if (chord == null) {
            return "none"
        }
        var displayName = chord.toString()

        if (chordOptions != null) {
            displayName += chordOptions?.getDisplayTensions()
            displayName += chordOptions?.getDisplayBaseNote(chord!!.rootNote)
        }
        return displayName
    }

    fun play(soundManager: SoundManager) {
        var noteSet: Set<Note> = chord!!.notes.toSet()
        noteSet = noteSet.plus(chordOptions!!.getTensionNotes(noteManager, chord!!.rootNote))
        noteSet = noteSet.plus(chordOptions!!.getBaseNote(noteManager, chord!!.rootNote))
        Timber.d("DEBUG: play: chord=${chord!!.notes} noteSet=$noteSet")
        noteSet.forEachIndexed { index, note ->
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

    fun getIntervalsSetOfChord(): Set<Intervals> {
        var intervalsSet = setOf<Intervals>()
        intervalsSet = intervalsSet.plus(chord!!.constitution.intervalSet
                .map { getIntervalFromNoteNo(getAppendedNoteNo(it.noteNo, chord!!.rootNote.noteNo)) })
        intervalsSet = intervalsSet.plus(chord!!.constitution.usableTensions
                .map { getIntervalFromNoteNo(getAppendedNoteNo(it.noteNo, chord!!.rootNote.noteNo)) })
        return intervalsSet
    }
}