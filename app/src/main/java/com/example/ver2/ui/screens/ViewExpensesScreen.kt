package com.example.ver2.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ver2.data.Expense
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewExpensesScreen(
    userId: String,
    onNavigateBack: () -> Unit,
    expenses: List<Expense>,
    totalExpense: String,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember { mutableStateOf<Expense?>(null) }
    Column {
        TopAppBar(
            title = { Text("View Expenses") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 4.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp
        ) {
            Text(
                text = "Total Expense: $totalExpense",
                style = MaterialTheme.typography.h5,
                color = Color.White,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center

            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Table header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Category",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Amount",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        // Expense rows
        LazyColumn {
            items(expenses) { expense ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 4.dp,
                    onClick = {
                        selectedExpense = expense
                        showDialog = true
                    }
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = expense.date,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = expense.category,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = expense.amount.toString(),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }

    if (showDialog && selectedExpense != null) {
        ExpenseDetailsDialog(
            expense = selectedExpense!!,
            onDialogClose = { showDialog = false },
            onEditExpense = { expense ->
                // Handle the edit action for the expense
            },
            onDeleteExpense = { expense ->
                // Handle the delete action for the expense
            }

        )
    }

}

@Composable
fun ExpenseDetailsDialog(
    expense: Expense,
    onDialogClose: () -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit

) {
    Dialog(onDismissRequest = onDialogClose) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(12.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Expense Details",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Category: ${expense.category}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Amount: ${expense.amount}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Date: ${expense.date}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Description: ${expense.description}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Payment mode: ${expense.paymentMode}",
                    style = MaterialTheme.typography.body1
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onEditExpense(expense)
                            onDialogClose()
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Edit")
                    }

                    Button(
                        onClick = {
                            onDeleteExpense(expense)
                            onDialogClose()
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Delete")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onDialogClose) {
                    Text("Close")
                }


            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}