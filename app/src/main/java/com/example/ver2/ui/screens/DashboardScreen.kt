package com.example.ver2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    navigateToSettings: () -> Unit,
    navigateToSplitExpense: () -> Unit,
    navigateToViewSharedExpenses: () -> Unit,
    navigateToReports: () -> Unit,
    totalExpense: String,
    totalSharedBalance: String,
    onAddExpenseClick: () -> Unit,
    onViewExpenseClick: () -> Unit
) {
    Column(){
        TopAppBar(
            title = { Text("Dashboard Screen") },

            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 4.dp,
        )
    }
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        SummaryCard(
            totalExpense = totalExpense,
            onAddExpenseClick = onAddExpenseClick,
            onViewExpenseClick = onViewExpenseClick
        )

        SharedExpensesCard(
            totalSharedBalance = totalSharedBalance,
            onSplitExpenseClick = navigateToSplitExpense,
            onViewSharedExpenseClick = navigateToViewSharedExpenses
        )

        Button(onClick = navigateToReports) {
            Text("Reports")
        }

        Button(onClick = navigateToSettings) {
            Text("Settings")
        }
    }
}


@Composable
fun SummaryCard(
    totalExpense: String,
    onAddExpenseClick: () -> Unit,
    onViewExpenseClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Expenses",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$totalExpense",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onAddExpenseClick) {
                    Text("Add Expense")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onViewExpenseClick) {
                    Text("View Expenses")
                }
            }
        }
    }
}

@Composable
fun SharedExpensesCard(
    totalSharedBalance: String,
    onSplitExpenseClick: () -> Unit,
    onViewSharedExpenseClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Shared Expenses",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$totalSharedBalance",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onSplitExpenseClick) {
                    Text("Split Expense")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onViewSharedExpenseClick) {
                    Text("View Shared Expenses")
                }
            }
        }
    }
}