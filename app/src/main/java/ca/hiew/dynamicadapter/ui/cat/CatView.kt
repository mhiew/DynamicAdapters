package ca.hiew.dynamicadapter.ui.cat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.UIView
import ca.hiew.dynamicadapter.util.exhaustive

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
        when (uiState) {
            is CatUIState.Success -> display(uiState.cat)
            is CatUIState.Loading -> display(uiState.cat)
            is CatUIState.Error -> display(uiState.errorMessage)
        }.exhaustive
    }

    private fun display(cat: Cat) = with(cat) {
        idTextView.visibility = VISIBLE
        idTextView.text = id.toString()
        nameTextView.text = name
    }

    private fun display(errorMessage: String?) {
        idTextView.visibility = GONE
        nameTextView.text = errorMessage
    }
}

