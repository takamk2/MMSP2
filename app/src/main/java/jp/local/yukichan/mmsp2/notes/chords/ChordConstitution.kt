package jp.local.yukichan.mmsp2.notes.chords

import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.notes.intervals.Intervals.*

/**
 * Created by takamk2 on 17/10/19.
 *
 * The Edit Fragment of Base Class.
 */
enum class ChordConstitution(val dispName: String, val intervalSet: Set<Intervals>, val usableTensions: Set<Intervals>) {
    Major("", setOf(Unison, M3, P5), setOf(M2, aug4)),
    MajorSixth("M6", setOf(Unison, M3, P5, M6), setOf(M2, aug4)),
    MajorSeventh("M7", setOf(Unison, M3, P5, M7), setOf(M2, aug4)),
    Seventh("7", setOf(Unison, M3, P5, m7), setOf(m2, M2, m3, aug4, m6, M6)),
    MajorFlatFive("-5", setOf(Unison, M3, dim5), setOf()),
    MajorSeventhFlatFive("M7-5", setOf(Unison, M3, dim5, M7), setOf()),
    Minor("m", setOf(Unison, m3, P5), setOf(M2, P4)),
    MinorMajorSixth("mM6", setOf(Unison, m3, P5, M6), setOf(M2, P4)),
    MinorMajorSeventh("mM7", setOf(Unison, m3, P5, M7), setOf(M2, P4)),
    MinorSeventh("m7", setOf(Unison, m3, P5, m7), setOf(M2, P4, M6)),
    MinorSeventhFlatFive("m7-5", setOf(Unison, m3, dim5, m7), setOf(M2, P4, m6)),
    Diminish("dim", setOf(Unison, m3, dim5), setOf(M2, P4, m6)),
    DiminishSeventh("dim7", setOf(Unison, m3, dim5, M6), setOf()),
    ;

    fun match(targetIntervalSet: Set<Intervals>): Boolean {
        return intervalSet == targetIntervalSet
    }
}