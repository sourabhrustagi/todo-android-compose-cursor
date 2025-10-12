package com.example.todoandroidcursor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoandroidcursor.data.api.model.LoginResponse
import com.example.todoandroidcursor.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    val isLoggedIn: StateFlow<Boolean> = authRepository.isLoggedIn
    val currentUser: StateFlow<com.example.todoandroidcursor.data.repository.UserInfo?> = authRepository.currentUser
    
    fun login(email: String, password: String, onResult: (LoginUiState) -> Unit) {
        viewModelScope.launch {
            onResult(LoginUiState(isLoading = true, errorMessage = null))
            
            val result = authRepository.login(email, password)
            
            result.fold(
                onSuccess = { loginResponse ->
                    if (loginResponse.success) {
                        onResult(LoginUiState(isLoading = false, isLoggedIn = true))
                    } else {
                        onResult(LoginUiState(isLoading = false, errorMessage = loginResponse.message))
                    }
                },
                onFailure = { exception ->
                    onResult(LoginUiState(isLoading = false, errorMessage = exception.message ?: "Login failed"))
                }
            )
        }
    }
    
    fun logout() {
        authRepository.logout()
    }
}
