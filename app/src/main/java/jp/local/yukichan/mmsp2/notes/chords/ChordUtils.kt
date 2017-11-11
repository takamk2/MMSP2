package jp.local.yukichan.mmsp2.notes.chords

import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.notes.scale.ScaleConstitution

/**
 * Created by takamk2 on 17/11/02.
 *
 * The Edit Fragment of Base Class.
 */
fun convertToConstitution(intervalList: List<Intervals>): ChordConstitution {
    val root = intervalList[0]
    val adjustedIntervalSet: Set<Intervals> = intervalList.map {
        val intervalSize = ScaleConstitution.All.intervalSet.size
        val noteNo = (it.noteNo - root.noteNo + intervalSize) % intervalSize
        getIntervalFromNoteNo(noteNo)
    }.toSet()
    return findChordConstitution(adjustedIntervalSet)
}

fun getIntervalFromNoteNo(noteNo: Int): Intervals {
    return Intervals.values().find { it.noteNo == noteNo && it.priority == 0 }!!
}

fun findChordConstitution(adjustedIntervalSet: Set<Intervals>): ChordConstitution {
    return ChordConstitution.values().find { it.match(adjustedIntervalSet) }!!
}
