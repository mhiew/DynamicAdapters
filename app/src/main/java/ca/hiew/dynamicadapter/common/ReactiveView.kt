package ca.hiew.dynamicadapter.common

import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface ReactiveView<Model : UIModel> : Consumer<Model>, ObservableSource<UIEvent>
