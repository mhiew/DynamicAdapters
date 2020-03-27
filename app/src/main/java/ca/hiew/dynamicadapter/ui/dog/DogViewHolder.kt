package ca.hiew.dynamicadapter.ui.dog

import android.content.Context
import ca.hiew.dynamicadapter.common.DynamicViewHolder

class DogViewHolder(private val dogView: DogView) : DynamicViewHolder<DogUIState>(dogView) {
    override fun bind(state: DogUIState) {
        dogView.display(state)
    }

    companion object {
        fun create(context: Context): DogViewHolder {
            val dogView = DogView(context)
            return DogViewHolder(dogView)
        }
    }
}
