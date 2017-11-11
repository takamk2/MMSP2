package jp.local.yukichan.mmsp2.notes.scale

import jp.local.yukichan.mmsp2.notes.Note
import jp.local.yukichan.mmsp2.notes.intervals.Intervals

/**
 * Created by takamk2 on 17/11/03.
 *
 * The Edit Fragment of Base Class.
 */
data class NoteOfScale(val scale: Scale, val note: Note) {

    val noteNo = note.noteNo

    val degreeName: String?
        get() {
            return getIntervals().degreeName
        }

    private val index: Int
        get() {
            return scale.notes.map { it.note }.indexOf(note)
        }

    private fun getIntervals(): Intervals {
        return scale.constitution.intervalSet.sortedBy { it.noteNo }[index]
    }
}
