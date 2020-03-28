package ca.hiew.dynamicadapter.ui.dog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.UIEvent
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.functions.Consumer

class DogView : ConstraintLayout, Consumer<DogUIState> {
    private val idTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_id) }
    private val nameTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_name) }
    private val button: Button by lazy { findViewById<Button>(R.id.dog_view_button) }

    constructor(context: Context?, attrs: AttributeSet? = null, eventRelay: PublishRelay<UIEvent>? = null) : super(context, attrs) {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.dog_view, this)
        setBackgroundResource(R.color.colorPrimaryDark)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun accept(uiState: DogUIState) {
        idTextView.text = uiState.id.toString()
        nameTextView.text = uiState.name
        button.text = uiState.buttonLabel
    }
}
