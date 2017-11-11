package jp.local.yukichan.mmsp2.notes

import jp.local.yukichan.mmsp2.events.KeyboardEvent
import timber.log.Timber

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
data class Note(val noteInfo: NoteInfo, val octave: Int = 0) {

    constructor(event: KeyboardEvent) : this(convertToNoteInfo(event.noteNo)!!, event.octave)

    constructor(noteNo: Int, octave: Int = 0) : this(convertToNoteInfo(noteNo)!!, octave)

    init {
        Timber.d("DEBUG: noteInfo=$noteInfo octave=$octave")
    }

    companion object {
        const val DIR_PATH: String = "sounds/"
        const val FILE_EXTENSION: String = ".wav"
    }

    val noteNo = noteInfo.noteNo

    val displayName: String
        get() = NoteInfo.values().find { it == noteInfo }!!.displayName

    val absoluteNoteNo: Int
        get() = octave * NoteConstants.INTERVAL + noteInfo.noteNo

    val filePath: String
        get() {
            Timber.d("DEBUG: noteInfo=$noteInfo")
            val name = NoteInfo.values()
                    .filter {
                        it.type != NoteInfo.Type.Flat
                    }
                    .find { it == noteInfo }!!.name.toLowerCase()
            return DIR_PATH + name + octave + FILE_EXTENSION
        }


    override fun toString(): String {
        return "$displayName($octave)"
    }

    fun sameAs(noteIdInInterval: Int, octave: Int): Boolean {
        return noteInfo.noteNo == noteIdInInterval && this.octave == octave
    }
}