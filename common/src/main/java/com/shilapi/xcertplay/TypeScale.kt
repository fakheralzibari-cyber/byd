package com.shilapi.xcertplay

/**
 * Text sizes for DiPlay's own screens, read at arm's length on a moving car's display.
 * Body text uses even steps from [CAPTION]; headlines from [HEADLINE] up keep their size.
 */
internal object TypeScale {
    const val CAPTION = 14
    const val BODY = 16
    const val HEADLINE = 26

    /** Nothing is smaller than [CAPTION], and odd body sizes round up to the next step. */
    fun sp(requested: Int): Int = when {
        requested >= HEADLINE -> requested
        requested <= CAPTION -> CAPTION
        else -> requested + requested % 2
    }
}
