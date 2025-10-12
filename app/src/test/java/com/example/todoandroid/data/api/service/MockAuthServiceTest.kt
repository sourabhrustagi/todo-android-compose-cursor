package com.example.todoandroid.data.api.service

import com.example.todoandroid.data.api.model.LoginRequest
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*

class MockAuthServiceTest {

    private val mockAuthService = MockAuthService()

    @Test
    fun `login with valid credentials should return success`() = runBlocking {
        val loginRequest = LoginRequest(
            email = "user@example.com",
            password = "password"
        )
        
        val response = mockAuthService.login(loginRequest)
        
        assertTrue("Response should be successful", response.isSuccessful)
        assertNotNull("Response body should not be null", response.body())
        
        val loginResponse = response.body()!!
        assertTrue("Login should be successful", loginResponse.success)
        assertEquals("Login successful", loginResponse.message)
        assertNotNull("Token should not be null", loginResponse.token)
        assertNotNull("User should not be null", loginResponse.user)
        assertEquals("user@example.com", loginResponse.user?.email)
    }

    @Test
    fun `login with invalid credentials should return failure`() = runBlocking {
        val loginRequest = LoginRequest(
            email = "wrong@example.com",
            password = "wrongpassword"
        )
        
        val response = mockAuthService.login(loginRequest)
        
        assertTrue("Response should be successful", response.isSuccessful)
        assertNotNull("Response body should not be null", response.body())
        
        val loginResponse = response.body()!!
        assertFalse("Login should fail", loginResponse.success)
        assertEquals("Invalid email or password", loginResponse.message)
        assertNull("Token should be null", loginResponse.token)
        assertNull("User should be null", loginResponse.user)
    }

    @Test
    fun `login with empty email should return failure`() = runBlocking {
        val loginRequest = LoginRequest(
            email = "",
            password = "password"
        )
        
        val response = mockAuthService.login(loginRequest)
        
        assertTrue("Response should be successful", response.isSuccessful)
        assertNotNull("Response body should not be null", response.body())
        
        val loginResponse = response.body()!!
        assertFalse("Login should fail", loginResponse.success)
        assertEquals("Email and password are required", loginResponse.message)
    }

    @Test
    fun `login with invalid email format should return failure`() = runBlocking {
        val loginRequest = LoginRequest(
            email = "invalid-email",
            password = "password"
        )
        
        val response = mockAuthService.login(loginRequest)
        
        assertTrue("Response should be successful", response.isSuccessful)
        assertNotNull("Response body should not be null", response.body())
        
        val loginResponse = response.body()!!
        assertFalse("Login should fail", loginResponse.success)
        assertEquals("Please enter a valid email address", loginResponse.message)
    }
}
