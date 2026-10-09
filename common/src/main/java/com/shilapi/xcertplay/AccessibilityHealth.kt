package com.shilapi.xcertplay

import android.content.Context
import android.provider.Settings

/** A feature the driver uses that stops working while its accessibility service is off. */
internal enum class AccessibilityGap { WHEEL_KEYS, USB_AUTO_ALLOW }

/**
 * BYD firmware updates and the car's app manager can switch DiPlay's accessibility services off without
 * telling anyone. The keys and the USB prompt then fail silently, so Home and Overview name the gap.
 */
internal object AccessibilityHealth {
    private const val USB_EXPECTED = "usb_auto_allow_expected"

    private fun prefs(context: Context) = context.getSharedPreferences("diplay", Context.MODE_PRIVATE)

    /** Only a feature the driver turned on can have a gap; a service nobody asked for is never reported. */
    fun gaps(
        wheelKeysWanted: Boolean,
        wheelKeysOn: Boolean,
        usbAutoAllowExpected: Boolean,
        usbAutoAllowOn: Boolean,
    ): List<AccessibilityGap> = buildList {
        if (wheelKeysWanted && !wheelKeysOn) add(AccessibilityGap.WHEEL_KEYS)
        if (usbAutoAllowExpected && !usbAutoAllowOn) add(AccessibilityGap.USB_AUTO_ALLOW)
    }

    fun gaps(context: Context): List<AccessibilityGap> {
        val usbOn = UsbPermissionSetup.Permission.ACCESSIBILITY.granted(context)
        // USB auto-allow has no switch of its own: once it has worked, the driver expects it to keep working.
        if (usbOn && !usbAutoAllowExpected(context)) setUsbAutoAllowExpected(context, true)
        return gaps(WheelKeyService.wanted(context), wheelKeysOn(context), usbAutoAllowExpected(context), usbOn)
    }

    fun usbAutoAllowExpected(context: Context): Boolean = prefs(context).getBoolean(USB_EXPECTED, false)

    /** The driver turned USB auto-allow off on purpose, so its absence is no longer a problem. */
    fun setUsbAutoAllowExpected(context: Context, expected: Boolean) {
        prefs(context).edit().putBoolean(USB_EXPECTED, expected).apply()
    }

    // A listed service with the master switch on is starting or restoring; only a missing one is off.
    private fun wheelKeysOn(context: Context): Boolean = WheelKeyService.connected() ||
        (WheelKeyService.enabledInSettings(context) && runCatching {
            Settings.Secure.getInt(context.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED, 0) == 1
        }.getOrDefault(false))
}
