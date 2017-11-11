package jp.local.yukichan.mmsp2.events

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
data class KeyboardEvent(val name: String = "Unknown",
                         val type: Type = Type.Unknown,
                         val noteNo: Int = -1,
                         val octave: Int = -1) {

    enum class Type {
        Unknown, KeyDown, KeyUp, Click, LongClick
    }
}
