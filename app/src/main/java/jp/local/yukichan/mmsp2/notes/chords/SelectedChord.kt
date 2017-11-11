package jp.local.yukichan.mmsp2.notes.chords

/**
 * Created by takamk2 on 17/10/19.
 *
 * The Edit Fragment of Base Class.
 */
data class SelectedChord(val id: Int) {

    var chord: Chord? = null

    override fun toString(): String {
        return "id=$id chord=$chord notes=${chord!!.notes}"
    }
}