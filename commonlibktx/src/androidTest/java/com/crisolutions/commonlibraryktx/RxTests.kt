package com.crisolutions.commonlibraryktx

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
        var testVariable = 0

        testRelay.relayOnTrue {
            testVariable = 1
        }

        assertTrue(testVariable == 1)
    }

    @Test
    fun testRelayOnTrueFailure() {

        val testRelay = BehaviorRelay.createDefault(false)
        var testVariable = 0

        testRelay.relayOnTrue {
            testVariable = 1
        }

        assertFalse(testVariable == 1)
    }

    @Test
    fun testRelayOnFalse() {

        val testRelay = BehaviorRelay.createDefault(false)
        var testVariable = 0

        testRelay.relayOnFalse {
            testVariable = 1
        }

        assertTrue(testVariable == 1)
    }

    @Test
    fun testRelayOnFalseFailure() {

        val testRelay = BehaviorRelay.createDefault(true)
        var testVariable = 0

        testRelay.relayOnFalse {
            testVariable = 1
        }

        assertFalse(testVariable == 1)
    }
}