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
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer

class DogView(
    context: Context?,
    attrs: AttributeSet? = null,
    private val eventRelay: PublishRelay<UIEvent> = PublishRelay.create()
) : ConstraintLayout(context, attrs),
    Consumer<DogUIState>,
    ObservableSource<UIEvent> {
    private val idTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_id) }
    private val nameTextView: TextView by lazy { findViewById<TextView>(R.id.dog_view_name) }
    private val button: Button by lazy { findViewById<Button>(R.id.dog_view_button) }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.dog_view, this)
        setBackgroundResource(R.color.colorPrimaryDark)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        button.setOnClickListener {
            eventRelay.accept(Event.DogButtonClicked)
        }
    }

    override fun accept(uiState: DogUIState) {
        idTextView.text = uiState.id.toString()
        nameTextView.text = uiState.name
        button.text = uiState.buttonLabel
    }

    override fun subscribe(observer: Observer<in UIEvent>) {
        eventRelay.subscribe(observer)
    }

    sealed class Event : UIEvent {
        object DogButtonClicked : Event()
    }
}
