package jp.local.yukichan.mmsp2.notes.scale

/**
 * Created by takamk2 on 17/10/19.
 *
 * The Edit Fragment of Base Class.
 */
data class SelectedScale(val id: Int) {

    var scale: Scale? = null

    override fun toString(): String {
        return "id=$id scale=${scale.toString()}"
    }
}