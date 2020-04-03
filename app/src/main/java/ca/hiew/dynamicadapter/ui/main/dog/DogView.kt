package ca.hiew.dynamicadapter.ui.main.dog

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ca.hiew.dynamicadapter.R
import ca.hiew.dynamicadapter.common.ui.UIEvent
import ca.hiew.dynamicadapter.common.ui.ReactiveView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observer

class DogView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    private val eventRelay: PublishRelay<UIEvent> = PublishRelay.create()
) : ConstraintLayout(context, attrs, defStyle),
    ReactiveView<DogUIModel>
{
    private val idTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_id) }
    private val nameTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_name) }
    private val button: Button by lazy { findViewById<Button>(R.id.dog_view_button) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.dog_view, this)
        setup()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        setBackgroundResource(R.color.colorPrimaryDark)
        button.setOnClickListener {
            eventRelay.accept(Event.DogButtonClicked)
        }
    }

    override fun accept(uiModel: DogUIModel) {
        idTextView.text = uiModel.id.toString()
        nameTextView.text = uiModel.name
        button.text = uiModel.buttonLabel
    }

    override fun subscribe(observer: Observer<in UIEvent>) {
        eventRelay.subscribe(observer)
    }

    sealed class Event : UIEvent {
        object DogButtonClicked : Event()
    }
}
