package com.shilapi.xcertplay.orchestration

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RfcommRetryPolicyTest {
    @Test
    fun quickFailureIsRetriedUntilTheAttemptLimit() {
        assertTrue(shouldRetryRfcommConnect(attempt = 1, elapsedMillis = 300))
        assertTrue(shouldRetryRfcommConnect(attempt = RFCOMM_CONNECT_ATTEMPTS - 1, elapsedMillis = 300))
        assertFalse(shouldRetryRfcommConnect(attempt = RFCOMM_CONNECT_ATTEMPTS, elapsedMillis = 300))
    }

    @Test
    fun slowFailureOrTimeoutIsNotRetried() {
        assertFalse(shouldRetryRfcommConnect(attempt = 1, elapsedMillis = RFCOMM_FAST_FAILURE_MILLIS))
        assertFalse(shouldRetryRfcommConnect(attempt = 1, elapsedMillis = 15_000))
    }
}
