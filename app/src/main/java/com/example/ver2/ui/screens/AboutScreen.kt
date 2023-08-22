package com.example.ver2.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Column(){
        TopAppBar(
            title = { Text("About This Project") },
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

        // Add your project information here
        Text(text = "App Name : Expense Manager for Students")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Description : \nTake charge of your financial future with " +
                "Expense Manager, the ultimate expense manager app. " +
                "Effortlessly track your expenses, set budgets, " +
                "and gain valuable insights into your spending habits. " +
                "Stay organized, save money, and achieve your goals " +
                "with this user-friendly and intuitive app.")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Team Members :" +
                "       \n1. Aditya Mandage" +
                "       \n2. Ganesh Dole" +
                "       \n3. Omkar Garad" +
                "       \n4. Shubham Patil" +
                "       \n5. Sarthak Jadhav")
        Spacer(modifier = Modifier.height(16.dp))


    }
}