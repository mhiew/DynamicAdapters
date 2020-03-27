package ca.hiew.dynamicadapter.ui.cat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.UIView

class CatView : ConstraintLayout, UIView<CatUIState> {
    private val idTextView: TextView
    private val nameTextView: TextView

    constructor(context: Context?, attrs: AttributeSet? = null) : super(context, attrs) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.cat_view, this)
        idTextView = findViewById(R.id.cat_view_id)
        nameTextView = findViewById(R.id.cat_view_name)
        setBackgroundResource(R.color.colorPrimary)
    }

    override fun display(uiState: CatUIState) {
        idTextView.text = uiState.id.toString()
        nameTextView.text = uiState.name
    }
}