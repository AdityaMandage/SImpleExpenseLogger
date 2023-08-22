package com.example.ver2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@Composable
fun ProfileScreen(onBackClick: () -> Unit, onSettingsBackClick: () -> Unit) {
    Column(){
        TopAppBar(
            title = { Text("Profile") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 4.dp,
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // You can add more profile-related content here

        Button(onClick = onBackClick) {
            Text("Back to Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}