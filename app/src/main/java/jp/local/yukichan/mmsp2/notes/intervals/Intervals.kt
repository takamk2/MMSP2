package jp.local.yukichan.mmsp2.notes.intervals

/**
 * Created by takamk2 on 17/10/25.
 *
 * The Edit Fragment of Base Class.
 */
enum class Intervals(val noteNo: Int, val priority: Int, val degreeName: String, val degreeTensionName: String,
                     val isTension: Boolean) {
    Unison(0, 0, "R", "", false),
    m2(1, 0, "m2", "♭9", true),
    M2(2, 0, "M2", "9", true),
    m3(3, 0, "m3", "♯9", false),
    M3(4, 0, "M3", "", false),
    P4(5, 0, "P4", "11", true),
    aug4(6, 1, "aug4", "♯11", true),
    dim5(6, 0, "dim5", "#11", false),
    P5(7, 0, "P5", "", false),
    aug5(8, 1, "aug5", "♭13", false),
    m6(8, 0, "m6", "♭13", true),
    M6(9, 0, "M6", "13", true),
    m7(10, 0, "m7", "", false),
    M7(11, 0, "M7", "", false),
    Octave(12, 0, "R", "", false)
}
