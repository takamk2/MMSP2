package jp.local.yukichan.mmsp2.notes

import jp.local.yukichan.mmsp2.notes.scale.ScaleConstitution
import timber.log.Timber

/**
 * Created by takamk2 on 17/11/02.
 *
 * The Edit Fragment of Base Class.
 */
fun getKeysOfScale(): List<NoteInfo> {
    return NoteInfo.values().filter { it.isKeyOfScale }
}

fun convertToNoteInfo(noteNo: Int, priorityType: NoteInfo.Type = NoteInfo.Type.Shape): NoteInfo? {
    return NoteInfo.values()
            .filter {
                Timber.d("convertToNoteInfo#filter: noteInfo=$it")
                it.type == NoteInfo.Type.Natural || it.type == priorityType
            }
            .find {
                it.noteNo == noteNo
            }
}

fun getAppendedNoteNo(noteNoA: Int, noteNoB: Int): Int {
    val intervalCount = getIntervalCount()
    return (noteNoA + noteNoB + intervalCount) % intervalCount
}

fun getIntervalCount(): Int {
    return ScaleConstitution.All.intervalSet.size
}
