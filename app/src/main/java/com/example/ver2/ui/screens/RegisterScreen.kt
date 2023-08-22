package com.example.ver2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterClick: (String, String, (String) -> Unit, () -> Unit) -> Unit,
    navigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Register", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onRegisterClick(email.text, password.text, { msg -> snackbarMessage = msg }, navigateToLogin) }) {
            Text("Register")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onNavigateBack) {
            Text("Back to Login")
        }
        if (snackbarMessage != null) {
            Snackbar(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                action = {
                    TextButton(onClick = { snackbarMessage = null }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(snackbarMessage!!)
            }
        }

    }
}