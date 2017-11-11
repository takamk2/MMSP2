package jp.local.yukichan.mmsp2.notes.intervals

import jp.local.yukichan.mmsp2.notes.chords.ChordConstitution
import jp.local.yukichan.mmsp2.notes.chords.ChordConstitution.*

/**
 * Created by takamk2 on 17/10/23.
 *
 * The Edit Fragment of Base Class.
 */
enum class MajorScaleIntervals(val intervalFromRoot: Int, val codeConstitutions: List<ChordConstitution>) {
    // TODO: このクラスを削除し、ScaleConstitutionを用いてScaleのコードを求める
    Unison(0, mutableListOf(Major, MajorSeventh)),
    Second(2, mutableListOf(Minor, MinorSeventh)),
    Third(4, mutableListOf(Minor, MinorSeventh)),
    Fourth(5, mutableListOf(Major, MajorSeventh)),
    Fifth(7, mutableListOf(Major, ChordConstitution.Seventh)),
    Sixth(9, mutableListOf(Minor, MinorSeventh)),
    Seventh(11, mutableListOf(Diminish, MinorSeventhFlatFive));
}

//    setOf(0, 2, 4, 5, 7, 9, 11),
