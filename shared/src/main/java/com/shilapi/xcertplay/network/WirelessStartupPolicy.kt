package com.shilapi.xcertplay.network

import java.io.IOException

object WirelessStartupPolicy {
    const val HOTSPOT_READY_MILLIS = 15_000L
    const val INTERFACE_POLL_MILLIS = 250L
    const val STABLE_SAMPLES = 3
    const val FIRST_TCP_MILLIS = 30_000L
    const val MAX_STARTUP_RETRIES = 5
    const val STABLE_SESSION_MILLIS = 60_000L
}

enum class WirelessStartupFailure {
    HOTSPOT_NOT_READY,
    FIRST_TCP_TIMEOUT,
    HOTSPOT_CONFIGURATION,

    /** The iPhone accepted CarPlay over Bluetooth but never appeared on the Wi-Fi network. */
    IPHONE_NOT_ON_WIFI,
}

/** Says which timeout the driver can act on: an iPhone that never joined points at Wi-Fi settings. */
fun firstTcpTimeoutFailure(iphoneSeenOnNetwork: Boolean?): WirelessStartupFailure =
    if (iphoneSeenOnNetwork == false) WirelessStartupFailure.IPHONE_NOT_ON_WIFI
    else WirelessStartupFailure.FIRST_TCP_TIMEOUT

class WirelessStartupException(val reason: WirelessStartupFailure, message: String) : IOException(message)
