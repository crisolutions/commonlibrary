package com.crisolutions.commonlibraryktx

import com.jakewharton.rxrelay2.BehaviorRelay
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

    @Test
    fun testRelayOnTrue() {

        val testRelay = BehaviorRelay.createDefault(true)

        testRelay.relayOnTrue {
            pass()
        }
    }

    @Test
    fun testRelayOnTrueFailure() {

        val testRelay = BehaviorRelay.createDefault(false)

        testRelay.relayOnTrue {
            fail()
        }
    }

    @Test
    fun testRelayOnFalse() {

        val testRelay = BehaviorRelay.createDefault(false)

        testRelay.relayOnFalse {
            pass()
        }
    }

    @Test
    fun testRelayOnFalseFailure() {

        val testRelay = BehaviorRelay.createDefault(true)

        testRelay.relayOnFalse {
            fail()
        }
    }
}