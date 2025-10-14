package com.example.todoandroid.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoandroid.viewmodel.AuthViewModel
import com.example.todoandroid.viewmodel.LoginUiState

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginState by remember { mutableStateOf(LoginUiState()) }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Sign in to your account",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(
                                text = if (passwordVisible) "👁️" else "👁️‍🗨️",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                )
                
                if (loginState.errorMessage != null) {
                    Text(
                        text = loginState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            loginState = loginState.copy(errorMessage = "Please fill in all fields")
                        } else if (!isValidEmail(email)) {
                            loginState = loginState.copy(errorMessage = "Please enter a valid email")
                        } else {
                            loginState = loginState.copy(errorMessage = null)
                            authViewModel.login(email, password) { newState ->
                                loginState = newState
                                if (newState.isLoggedIn) {
                                    onLoginSuccess()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = !loginState.isLoading
                ) {
                    if (loginState.isLoading) {
                        Text("Signing in...")
                    } else {
                        Text("Sign In")
                    }
                }
                
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Demo credentials:\nEmail: user@example.com\nPassword: password",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    return email.matches(Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"))
}


