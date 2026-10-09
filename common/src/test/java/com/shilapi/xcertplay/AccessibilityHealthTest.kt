package com.shilapi.xcertplay

import org.junit.Assert.assertEquals
import org.junit.Test

class AccessibilityHealthTest {
    @Test fun aServiceNobodyAskedForIsNeverReported() {
        assertEquals(emptyList<AccessibilityGap>(), AccessibilityHealth.gaps(
            wheelKeysWanted = false, wheelKeysOn = false, usbAutoAllowExpected = false, usbAutoAllowOn = false))
    }

    @Test fun wheelKeysInUseWithTheServiceOffAreReported() {
        assertEquals(listOf(AccessibilityGap.WHEEL_KEYS), AccessibilityHealth.gaps(
            wheelKeysWanted = true, wheelKeysOn = false, usbAutoAllowExpected = false, usbAutoAllowOn = false))
    }

    @Test fun usbAutoAllowThatStoppedWorkingIsReported() {
        assertEquals(listOf(AccessibilityGap.USB_AUTO_ALLOW), AccessibilityHealth.gaps(
            wheelKeysWanted = false, wheelKeysOn = false, usbAutoAllowExpected = true, usbAutoAllowOn = false))
    }

    @Test fun runningServicesHaveNoGap() {
        assertEquals(emptyList<AccessibilityGap>(), AccessibilityHealth.gaps(
            wheelKeysWanted = true, wheelKeysOn = true, usbAutoAllowExpected = true, usbAutoAllowOn = true))
    }

    @Test fun bothGapsAreReportedTogether() {
        assertEquals(listOf(AccessibilityGap.WHEEL_KEYS, AccessibilityGap.USB_AUTO_ALLOW), AccessibilityHealth.gaps(
            wheelKeysWanted = true, wheelKeysOn = false, usbAutoAllowExpected = true, usbAutoAllowOn = false))
    }
}
