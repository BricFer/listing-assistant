package com.bricfern.service

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlinx.serialization.SerializationException

class MockListingServiceTest {

    @Test
    fun `sendPromptMock throws SerializationException when mock file has invalid data types`() {

        val service = MockListingService("malformed")

        assertFailsWith<SerializationException> {
            service.sendPromptMock("cualquier descripción")
        }
    }
}