package com.example.ver2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    onResetPasswordClick: (String, (String) -> Unit) -> Unit
)  {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Reset Password", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onResetPasswordClick(email.text) { msg -> snackbarMessage = msg } }) {
            Text("Reset Password")
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