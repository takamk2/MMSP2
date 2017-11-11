package jp.local.yukichan.mmsp2.notes

/**
 * Created by takamk2 on 17/11/02.
 *
 * The Edit Fragment of Base Class.
 */
enum class NoteInfo(val id: Int, val noteNo: Int, val displayName: String, val type: Type,
                    val isKeyOfScale: Boolean, val relationId: Int? = null) {
    C(0, 0, "C", Type.Natural, true),
    Cs(1, 1, "C♯", Type.Shape, false, 2),
    Df(2, 1, "D♭", Type.Flat, true, 1),
    D(3, 2, "D", Type.Natural, true),
    Ds(4, 3, "D♯", Type.Shape, false, 5),
    Ef(5, 3, "E♭", Type.Flat, true, 4),
    E(6, 4, "E", Type.Natural, true),
    F(7, 5, "F", Type.Natural, true),
    Fs(8, 6, "F♯", Type.Shape, true, 9),
    Gf(9, 6, "G♭", Type.Flat, false, 8),
    G(10, 7, "G", Type.Natural, true),
    Gs(11, 8, "G♯", Type.Shape, false, 12),
    Af(12, 8, "A♭", Type.Flat, true, 11),
    A(13, 9, "A", Type.Natural, true),
    As(14, 10, "A♯", Type.Shape, false, 15),
    Bf(15, 10, "B♭", Type.Flat, true, 14),
    B(16, 11, "B", Type.Natural, true),
    ;

    enum class Type {
        Natural,
        Shape,
        Flat,
        ;
    }
}
