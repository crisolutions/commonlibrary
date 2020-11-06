package com.crisolutions.commonlibraryktx

import com.jakewharton.rxrelay3.BehaviorRelay
import io.reactivex.rxjava3.core.Observable


fun Observable<String>.filterIfNotEmpty(): Observable<String> = filter { it.isNotEmpty() }

fun BehaviorRelay<Boolean>.relayOnTrue(block: () -> (Unit)) {
    this.value?.let {
        if (it) block.invoke()
    }
}

fun BehaviorRelay<Boolean>.relayOnFalse(block: () -> Unit) {
    this.value?.let {
        if (!it) block.invoke()
    }
}

fun <T> T.toObservable(): Observable<T> = Observable.just(this)