package jp.local.yukichan.mmsp2.notes

import android.util.Log

/**
 * Created by takamk2 on 17/10/17.
 *
 * The Edit Fragment of Base Class.
 */
data class SelectedNotes(val id: Int) {

    private var notes: Set<Note> = mutableSetOf()

    private val LOGTAG: String = SelectedNotes::class.java.simpleName

    override fun toString(): String {
        return "id=$id intervalSet=$notes"
    }

    fun addNote(note: Note) {
        notes = notes.plus(note)
        Log.d(LOGTAG, "plus note=$note intervalSet=$notes")
    }

    fun removeNote(note: Note) {
        notes = notes.minus(note)
        Log.d(LOGTAG, "minus note=$note intervalSet=$notes")
    }

    fun contains(note: Note): Boolean {
        Log.d(LOGTAG, "contains note=$note intervalSet=$notes res=${notes.contains(note)}")
        return notes.contains(note)
    }

    fun getNoteList(): List<Note> {
        return notes.toList()
    }

    fun getIntervalNoteNoSet(): Set<Int> {
        return notes.map { it.noteNo }.toSet()
    }
}