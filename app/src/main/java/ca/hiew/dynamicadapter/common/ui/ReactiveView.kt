package ca.hiew.dynamicadapter.common.ui

import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface ReactiveView<Model : UIModel> : Consumer<Model>, ObservableSource<UIEvent>
