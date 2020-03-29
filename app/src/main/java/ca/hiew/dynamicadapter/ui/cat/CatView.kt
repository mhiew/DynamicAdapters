package ca.hiew.dynamicadapter.ui.cat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.UIEvent
import ca.hiew.dynamicadapter.common.UIView
import ca.hiew.dynamicadapter.util.exhaustive
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observer
import io.reactivex.functions.Consumer

class CatView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    private val eventRelay: PublishRelay<UIEvent> = PublishRelay.create()
) :
    ConstraintLayout(context, attrs, defStyle),
    UIView<CatUIState> {
    private val idTextView: TextView by lazy { findViewById<TextView>(R.id.cat_view_id) }
    private val nameTextView: TextView by lazy { findViewById<TextView>(R.id.cat_view_name) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.cat_view, this)
        setBackgroundResource(R.color.colorPrimary)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun accept(state: CatUIState) {
        when (state) {
            is CatUIState.Success -> display(state.cat)
            is CatUIState.Loading -> display(state.cat)
            is CatUIState.Error -> display(state.errorMessage)
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

    override fun subscribe(observer: Observer<in UIEvent>) {
        //no-op
    }
}

