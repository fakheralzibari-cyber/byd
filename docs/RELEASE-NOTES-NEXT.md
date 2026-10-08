# DiPlay next release notes

Changes through DiPlay 0.2.15 are documented in [0.2.15 release notes](RELEASE-NOTES-0.2.15.md). Measured checks are in [VALIDATION.md](VALIDATION.md). Device acceptance and remaining failure families are tracked in [connection reliability validation](CONNECTION_RELIABILITY.md).

## Wireless connection reliability (modified 2026-10-08)

- **Car hotspot password.** When the head unit exposes its hotspot configuration, DiPlay now sends the car's real hotspot password to the iPhone instead of a saved password that no longer matches. Diagnostics record `passphraseReadable` and `passphraseSource=saved|car`; the password itself is never logged. Most firmware hides the password from apps, so the saved one stays in use there.
- **Clearer failure when the iPhone never joins Wi-Fi.** If the iPhone accepts CarPlay over Bluetooth but its CarPlay service never appears on the hotspot within 30 s, the status now reads that the iPhone did not join the car's Wi-Fi and names the two usual causes (saved hotspot name/password, iPhone Auto-Join). The diagnostic line is `wireless first TCP timeout iphoneSeenOnNetwork=…`.
- **Bluetooth setup timeouts recover.** A Bluetooth bootstrap that times out before any handoff now tears down the hotspot, Bonjour and RFCOMM resources and uses the normal retry path, instead of leaving them open until the driver acts.
- **Renamed iPhones are found.** A paired phone whose name does not contain "iPhone" is recognized by its cached CarPlay iAP2 Bluetooth service.
- **Quick RFCOMM failures are retried.** A Bluetooth connect failure within 5 s is retried up to three times, 1.5 s apart, without rebuilding the Wi-Fi network. Timeouts are not retried. Reports show `attempt=` on each Bluetooth connect line.

These are source changes with unit coverage; they have not yet been tested on a vehicle.
