package jp.local.yukichan.mmsp2.notes.scale

import android.util.Log
import jp.local.yukichan.mmsp2.notes.intervals.Intervals
import jp.local.yukichan.mmsp2.notes.intervals.Intervals.*

/**
 * Created by takamk2 on 17/10/18.
 *
 * The Edit Fragment of Base Class.
 */
enum class ScaleConstitution(val dispName: String, val intervalSet: Set<Intervals>,
                             val degreeNameList: List<String>? = null) {
    All("All",
            setOf(Unison, m2, M2, m3, M3, P4,
                    dim5, P5, m6, M6, m7, M7)),
    MajorScale("Major scale",
            setOf(Unison, M2, M3, P4, P5, M6, M7),
            listOf("R", "II/IX", "III", "IV/XI", "V", "VI/XIII", "VII")),
    ;

    fun getRelativePositionInterval(from: Intervals, position: Int): Intervals {
        val list = getSortedIntervals()
        val fromIndex = list.indexOf(from)
        val index = (fromIndex + position + list.size) % list.size
        Log.d(javaClass.simpleName, "getRelativePositionInterval(from=$from, position=$position) fromIndex=$fromIndex index=$index")
        return list[index]
    }

    private fun getSortedIntervals(): List<Intervals> {
        return intervalSet.sortedBy { it.noteNo }
    }
}