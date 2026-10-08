package com.shilapi.xcertplay.network

import org.junit.Assert.assertEquals
import org.junit.Test

class WirelessStartupFailureTest {
    @Test
    fun iphoneThatNeverAppearedOnWifiPointsAtWifiSettings() {
        assertEquals(WirelessStartupFailure.IPHONE_NOT_ON_WIFI, firstTcpTimeoutFailure(iphoneSeenOnNetwork = false))
    }

    @Test
    fun iphoneSeenOnWifiOrUnknownKeepsTheGenericTimeout() {
        assertEquals(WirelessStartupFailure.FIRST_TCP_TIMEOUT, firstTcpTimeoutFailure(iphoneSeenOnNetwork = true))
        assertEquals(WirelessStartupFailure.FIRST_TCP_TIMEOUT, firstTcpTimeoutFailure(iphoneSeenOnNetwork = null))
    }

    @Test
    fun readableCarPasswordReplacesAStaleSavedPassword() {
        assertEquals("car-password", effectiveManualHotspotPassphrase(saved = "old-password", car = "car-password"))
        assertEquals("same-password", effectiveManualHotspotPassphrase(saved = "same-password", car = "same-password"))
    }

    @Test
    fun hiddenOrMalformedCarPasswordKeepsTheSavedPassword() {
        assertEquals("saved-password", effectiveManualHotspotPassphrase(saved = "saved-password", car = null))
        assertEquals("saved-password", effectiveManualHotspotPassphrase(saved = "saved-password", car = ""))
        assertEquals("saved-password", effectiveManualHotspotPassphrase(saved = "saved-password", car = "*"))
        assertEquals("saved-password", effectiveManualHotspotPassphrase(saved = "saved-password", car = "x".repeat(64)))
    }
}
