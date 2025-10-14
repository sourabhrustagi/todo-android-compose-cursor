package com.example.todoandroid.data.api.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.core.JsonProcessingException
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class LoginRequestTest {

    private lateinit var objectMapper: ObjectMapper

    @Before
    fun setUp() {
        objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    }

    @Test
    fun `LoginRequest should have correct properties`() {
        // Given
        val email = "user@example.com"
        val password = "password123"

        // When
        val loginRequest = LoginRequest(email = email, password = password)

        // Then
        assertEquals("Email should match", email, loginRequest.email)
        assertEquals("Password should match", password, loginRequest.password)
    }

    @Test
    fun `LoginRequest should serialize to correct JSON`() {
        // Given
        val loginRequest = LoginRequest(
            email = "user@example.com",
            password = "password123"
        )

        // When
        val json = objectMapper.writeValueAsString(loginRequest)

        // Then
        assertTrue("JSON should contain email field", json.contains("\"email\""))
        assertTrue("JSON should contain password field", json.contains("\"password\""))
        assertTrue("JSON should contain email value", json.contains("user@example.com"))
        assertTrue("JSON should contain password value", json.contains("password123"))
    }

    @Test
    fun `LoginRequest should deserialize from correct JSON`() {
        // Given
        val json = """
            {
                "email": "user@example.com",
                "password": "password123"
            }
        """.trimIndent()

        // When
        val loginRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertNotNull("LoginRequest should not be null", loginRequest)
        assertEquals("Email should match", "user@example.com", loginRequest.email)
        assertEquals("Password should match", "password123", loginRequest.password)
    }

    @Test
    fun `LoginRequest should handle empty strings`() {
        // Given
        val loginRequest = LoginRequest(email = "", password = "")

        // When
        val json = objectMapper.writeValueAsString(loginRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Empty email should be preserved", "", deserializedRequest.email)
        assertEquals("Empty password should be preserved", "", deserializedRequest.password)
    }

    @Test
    fun `LoginRequest should handle special characters in email`() {
        // Given
        val email = "user+test@example-domain.co.uk"
        val password = "password123"
        val loginRequest = LoginRequest(email = email, password = password)

        // When
        val json = objectMapper.writeValueAsString(loginRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Special characters in email should be preserved", email, deserializedRequest.email)
        assertEquals("Password should be preserved", password, deserializedRequest.password)
    }

    @Test
    fun `LoginRequest should handle special characters in password`() {
        // Given
        val email = "user@example.com"
        val password = "p@ssw0rd!@#$%^&*()"
        val loginRequest = LoginRequest(email = email, password = password)

        // When
        val json = objectMapper.writeValueAsString(loginRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Email should be preserved", email, deserializedRequest.email)
        assertEquals("Special characters in password should be preserved", password, deserializedRequest.password)
    }

    @Test
    fun `LoginRequest should handle long strings`() {
        // Given
        val longEmail = "very.long.email.address.that.might.be.used.in.some.systems@very-long-domain-name.example.com"
        val longPassword = "a".repeat(100) // 100 character password
        val loginRequest = LoginRequest(email = longEmail, password = longPassword)

        // When
        val json = objectMapper.writeValueAsString(loginRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Long email should be preserved", longEmail, deserializedRequest.email)
        assertEquals("Long password should be preserved", longPassword, deserializedRequest.password)
    }

    @Test
    fun `LoginRequest should handle Unicode characters`() {
        // Given
        val email = "tëst@éxämplé.com"
        val password = "pässwörd"
        val loginRequest = LoginRequest(email = email, password = password)

        // When
        val json = objectMapper.writeValueAsString(loginRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Unicode email should be preserved", email, deserializedRequest.email)
        assertEquals("Unicode password should be preserved", password, deserializedRequest.password)
    }

    @Test
    fun `LoginRequest should be equal when properties are same`() {
        // Given
        val loginRequest1 = LoginRequest(email = "user@example.com", password = "password")
        val loginRequest2 = LoginRequest(email = "user@example.com", password = "password")

        // When & Then
        assertEquals("LoginRequests with same properties should be equal", loginRequest1, loginRequest2)
        assertEquals("Hash codes should be equal", loginRequest1.hashCode(), loginRequest2.hashCode())
    }

    @Test
    fun `LoginRequest should not be equal when properties are different`() {
        // Given
        val loginRequest1 = LoginRequest(email = "user@example.com", password = "password")
        val loginRequest2 = LoginRequest(email = "user@example.com", password = "different")

        // When & Then
        assertNotEquals("LoginRequests with different properties should not be equal", loginRequest1, loginRequest2)
    }

    @Test
    fun `LoginRequest toString should contain both email and password`() {
        // Given
        val loginRequest = LoginRequest(email = "user@example.com", password = "password123")

        // When
        val stringRepresentation = loginRequest.toString()

        // Then
        assertTrue("toString should contain email", stringRepresentation.contains("user@example.com"))
        assertTrue("toString should contain password", stringRepresentation.contains("password123"))
    }

    @Test
    fun `LoginRequest should handle extra fields in JSON gracefully`() {
        // Given
        val jsonWithExtraFields = """
            {
                "email": "user@example.com",
                "password": "password123",
                "invalidField": "value",
                "anotherField": 123
            }
        """.trimIndent()

        // When & Then - Jackson will throw exception for unknown properties by default
        try {
            objectMapper.readValue(jsonWithExtraFields, LoginRequest::class.java)
            fail("Should have thrown exception for unknown properties")
        } catch (e: Exception) {
            // Expected behavior - Jackson doesn't allow unknown properties by default
            assertTrue("Should be UnrecognizedPropertyException or similar", 
                e is com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException)
        }
    }

    @Test
    fun `LoginRequest should handle null values gracefully`() {
        // Given
        val jsonWithNulls = """
            {
                "email": null,
                "password": null
            }
        """.trimIndent()

        // When & Then - Jackson will throw exception for null values on non-nullable properties
        try {
            objectMapper.readValue(jsonWithNulls, LoginRequest::class.java)
            fail("Should have thrown exception for null values")
        } catch (e: Exception) {
            // Expected behavior - Jackson doesn't allow null for non-nullable properties
            assertTrue("Should be MissingKotlinParameterException or similar", 
                e is com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException)
        }
    }

    @Test
    fun `LoginRequest should handle missing fields gracefully`() {
        // Given
        val jsonWithMissingFields = """
            {
                "email": "user@example.com"
            }
        """.trimIndent()

        // When & Then - Jackson will throw exception for missing required fields
        try {
            objectMapper.readValue(jsonWithMissingFields, LoginRequest::class.java)
            fail("Should have thrown exception for missing fields")
        } catch (e: Exception) {
            // Expected behavior - Jackson requires all non-nullable properties
            assertTrue("Should be MissingKotlinParameterException or similar", 
                e is com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException)
        }
    }


    @Test(expected = JsonProcessingException::class)
    fun `LoginRequest should throw exception for malformed JSON`() {
        // Given
        val malformedJson = """
            {
                "email": "user@example.com",
                "password": "password123"
                // Missing closing brace
        """.trimIndent()

        // When & Then - Should throw JsonProcessingException
        objectMapper.readValue(malformedJson, LoginRequest::class.java)
    }

    @Test
    fun `LoginRequest should maintain data integrity through serialization cycle`() {
        // Given
        val originalRequest = LoginRequest(
            email = "test@example.com",
            password = "testPassword123"
        )

        // When
        val json = objectMapper.writeValueAsString(originalRequest)
        val deserializedRequest = objectMapper.readValue(json, LoginRequest::class.java)

        // Then
        assertEquals("Original and deserialized should be equal", originalRequest, deserializedRequest)
        assertEquals("Email should be preserved", originalRequest.email, deserializedRequest.email)
        assertEquals("Password should be preserved", originalRequest.password, deserializedRequest.password)
    }
}
