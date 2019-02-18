package com.crisolutions.commonlibraryktx

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

fun Observable<String>.filterIfNotEmpty(): Observable<String> = filter { it.isNotEmpty() }

fun BehaviorRelay<Boolean>.relayOnTrue(block: () -> (Unit)) {
    this.value?.let {
        if (it) block.invoke()
    }
}