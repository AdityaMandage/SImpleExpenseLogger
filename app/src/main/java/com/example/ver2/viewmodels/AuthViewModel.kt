package com.example.ver2.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    val errorMessage = mutableStateOf("")
    private val _authState = MutableStateFlow<AuthState>(AuthState.Empty)

    val authState: StateFlow<AuthState> = _authState


    fun register(email: String, password: String, showMessage: (String) -> Unit,navigateToLogin: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showMessage("Registration successful!")
                navigateToLogin()

            } else {
                showMessage("Registration failed: ${task.exception?.localizedMessage}")
            }
        }
    }

    fun login(email: String, password: String,navigateToDashboard: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        navigateToDashboard()
                    } else {
                        // Login failed
                        errorMessage.value = task.exception?.message ?: "Login failed"
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Login failed"
            }
        }
    }

    fun resetPassword(email: String, showMessage: (String) -> Unit){

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showMessage("Password reset email sent!")
            } else {
                showMessage("Failed to send reset email: ${task.exception?.localizedMessage}")
            }
        }


    }
    fun signOut() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    sealed class AuthState {
        object Empty : AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        data class Failure(val message: String) : AuthState()
    }
}