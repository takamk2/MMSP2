package jp.local.yukichan.mmsp2.notes.scale

import android.util.Log
import jp.local.yukichan.mmsp2.notes.*
import jp.local.yukichan.mmsp2.sounds.SoundManager

/**
 * Created by takamk2 on 17/10/18.
 *
 * The Edit Fragment of Base Class.
 */
data class Scale(private val noteManager: NoteManager,
                 val rootNote: Note,
                 val constitution: ScaleConstitution) {

    val type = rootNote.noteInfo.type
    val notes = mutableListOf<NoteOfScale>() // TODO: <NoteOfScale> 内包 Note

    init {
        constitution.intervalSet.mapTo(notes) {
            val noteNo = getAppendedNoteNo(rootNote.noteNo, it.noteNo)
            val noteInfo = convertToNoteInfo(noteNo, type) // convert Flat to Shape
            if (noteInfo != null) {
                NoteOfScale(this, noteManager.get(noteInfo, rootNote.octave))
            } else {
                NoteOfScale(this, noteManager.get(noteNo, rootNote.octave))
            }
        }
    }

    override fun toString(): String {
        return rootNote.displayName + " " + constitution.dispName
    }

    fun getIntervalNoteNoSet(): Set<Int> {
        return notes.map { it.noteNo }.toSet()
    }

    fun play(soundManager: SoundManager) {
        val n = mutableListOf<Note>()
        n.addAll(notes.map { it.note })
        n.add(noteManager.get(rootNote.noteNo, rootNote.octave + 1))
        n.forEach {
            soundManager.play(it, 0.8f, 0.8f)
            Thread.sleep(500)
        }
    }
}