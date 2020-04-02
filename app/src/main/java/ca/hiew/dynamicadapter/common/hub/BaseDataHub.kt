package ca.hiew.dynamicadapter.common.hub

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource
import io.reactivex.Observer

abstract class BaseDataHub<Action : Any, State : Any, Announcement : Any>(
    val stateRelay: BehaviorRelay<State> = BehaviorRelay.create(),
    val announcementRelay: PublishRelay<Announcement> = PublishRelay.create()
) : DataHub<Action, State, Announcement> {
    override val announcement: ObservableSource<Announcement>
        get() = announcementRelay

    override fun subscribe(observer: Observer<in State>) {
        stateRelay.subscribe(observer)
    }
}

