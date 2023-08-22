package com.example.ver2.ui.screens

import androidx.compose.runtime.Composable
import com.example.ver2.data.Expense

@Composable
fun EditExpenseScreen(
    expense: Expense,
    onNavigateBack: () -> Unit,
    onUpdateExpenseClick: (Expense) -> Unit
) {
    // You can reuse the layout and logic from AddExpenseScreen
    // and modify it to prefill the information and update the expense
    // Add an IconButton to close the screen, and a button to update the expense
}