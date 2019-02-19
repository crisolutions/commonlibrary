package com.crisolutions.commonlibraryktx

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class RxTests {

    @Test
    fun testFilterIfNotEmpty() {
        val testObserver: TestObserver<String> =
                Observable.just("NotEmpty")
                        .filterIfNotEmpty()
                        .test()

        testObserver.assertValue("NotEmpty")
    }

    @Test
    fun testFilterIfNotEmptyWithEmpty() {
        val testObserver: TestObserver<String> =
                Observable.just("")
                        .filterIfNotEmpty()
                        .test()

        testObserver.assertNoValues()
    }
}