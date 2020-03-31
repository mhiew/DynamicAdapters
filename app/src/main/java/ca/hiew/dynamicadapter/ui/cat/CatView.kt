package ca.hiew.dynamicadapter.ui.cat

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.UIEvent
import ca.hiew.dynamicadapter.common.ReactiveView
import ca.hiew.dynamicadapter.util.exhaustive
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.Observer

class CatView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attrs, defStyle),
    ReactiveView<CatUIModel> {
    private val idTextView: TextView by lazy { findViewById<TextView>(R.id.cat_view_id) }
    private val nameTextView: TextView by lazy { findViewById<TextView>(R.id.cat_view_name) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.cat_view, this)
        setup()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        setBackgroundResource(R.color.colorPrimary)
    }

    override fun subscribe(observer: Observer<in UIEvent>) {
        val clickEvent = this.clicks().map { Event.CatViewClicked }
        val nameClickEvent = nameTextView.clicks().map { Event.CatNameClicked }
        Observable.merge(clickEvent, nameClickEvent).subscribe(observer)
    }

    override fun accept(uiModel: CatUIModel) {
        when (uiModel) {
            is CatUIModel.Success -> display(uiModel.cat)
            is CatUIModel.Loading -> display(uiModel.cat)
            is CatUIModel.Error -> display(uiModel.errorMessage)
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

    sealed class Event : UIEvent {
        object CatViewClicked : Event()
        object CatNameClicked: Event()
    }
}

