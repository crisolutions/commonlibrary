package com.crisolutions.commonlibraryktx

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import junit.framework.Assert.*
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
        var value = false

        testRelay.relayOnTrue {
            value = true
        }

        assertTrue(value)
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
        var value = true

        testRelay.relayOnFalse {
            value = false
        }

        assertFalse(value)
    }

    @Test
    fun testRelayOnFalseFailure() {

        val testRelay = BehaviorRelay.createDefault(true)

        testRelay.relayOnFalse {
            fail()
        }
    }

    @Test
    fun testToObservable() {
        val name = "Dimly".toObservable()
        name.test().assertValue("Dimly")
    }
}