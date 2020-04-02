package ca.hiew.dynamicadapter.common.hub

import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface DataHub<Action : Any, State : Any, Announcement : Any> : Consumer<Action>, ObservableSource<State> {
    val announcement: ObservableSource<Announcement>
}


