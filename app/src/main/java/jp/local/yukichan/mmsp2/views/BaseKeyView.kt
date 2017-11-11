package jp.local.yukichan.mmsp2.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import jp.local.yukichan.mmsp2.R

/**
 * Created by takamk2 on 17/10/14.
 *
 * The Edit Fragment of Base Class.
 */
abstract class BaseKeyView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
    : KeyView,
        FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    override var noteNo: Int = -1

    internal var name: String? = null

    init {
        if (attrs != null) {
            // get custom attributes
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.KeyView, 0, 0)
            name = typedArray.getString(R.styleable.KeyView_name)
            noteNo = typedArray.getInt(R.styleable.KeyView_note_no, -1)
        }
    }
}


