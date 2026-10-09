package com.shilapi.xcertplay

import org.junit.Assert.assertEquals
import org.junit.Test

class TypeScaleTest {
    @Test fun smallTextIsRaisedToTheCaptionSize() {
        assertEquals(14, TypeScale.sp(12))
        assertEquals(14, TypeScale.sp(13))
        assertEquals(14, TypeScale.sp(14))
    }

    @Test fun oddBodySizesRoundUpToTheNextStep() {
        assertEquals(16, TypeScale.sp(15))
        assertEquals(16, TypeScale.sp(16))
        assertEquals(18, TypeScale.sp(17))
        assertEquals(20, TypeScale.sp(19))
        assertEquals(24, TypeScale.sp(24))
    }

    @Test fun headlinesKeepTheirSize() {
        assertEquals(26, TypeScale.sp(26))
        assertEquals(36, TypeScale.sp(36))
        assertEquals(42, TypeScale.sp(42))
    }
}
