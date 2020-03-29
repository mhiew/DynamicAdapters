package ca.hiew.dynamicadapter.common

import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface UIView<S : UIState> : Consumer<S>, ObservableSource<UIEvent>
