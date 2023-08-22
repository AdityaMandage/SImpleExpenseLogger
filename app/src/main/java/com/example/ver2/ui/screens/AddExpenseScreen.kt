package com.example.ver2.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ver2.data.Expense
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddExpenseScreen(
    onNavigateBack: () -> Unit,
    onAddExpenseClick: (Expense, (String) -> Unit, (String) -> Unit) -> Unit
) {
    val categoryItems = listOf("Food", "Transport", "Entertainment", "Bills", "Others")
    val paymentModes = listOf("Cash", "Credit Card", "Debit Card", "Net Banking", "Others")

    var selectedCategory by remember { mutableStateOf(categoryItems[0]) }
    var selectedPaymentMode by remember { mutableStateOf(paymentModes[0]) }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var snackbarMessage by remember { mutableStateOf("") }
    Column(){
        TopAppBar(
            title = { Text("Add Expense") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            elevation = 4.dp,
        )
    }
    Column(
        modifier = Modifier.padding(16.dp)
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuComponent(
            items = categoryItems,
            selectedItem = selectedCategory,
            onItemSelected = { selectedCategory = it },
            labelText = "Category"
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") })
        Spacer(modifier = Modifier.height(16.dp))

        DatePickerComponent(onDateChange = { date = it })
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") })
        Spacer(modifier = Modifier.height(16.dp))

        DropdownMenuComponent(
            items = paymentModes,
            selectedItem = selectedPaymentMode,
            onItemSelected = { selectedPaymentMode = it },
            labelText = "Payment Mode"
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val expense = Expense(
                    category = selectedCategory,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    date = date,
                    description = description,
                    paymentMode = selectedPaymentMode
                )
                onAddExpenseClick(expense,
                    { snackbarMessage = it }, // success callback
                    { snackbarMessage = it }  // error callback
                )
            },
            enabled = amount.isNotEmpty() && date.isNotEmpty()
        ) {
            Text("Add Expense")
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (snackbarMessage.isNotEmpty()) {
            Snackbar(
                modifier = Modifier.padding(8.dp),
                action = {
                    TextButton(onClick = { snackbarMessage = "" }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(snackbarMessage)
            }
        }
    }
}




@Composable
fun DropdownMenuComponent(items: List<String>, selectedItem: String, onItemSelected: (String) -> Unit, labelText: String) {
    var expanded by remember { mutableStateOf(false) }
    val textStyle = MaterialTheme.typography.body1

    Box {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            label = { Text(labelText) },
            textStyle = textStyle,
            readOnly = true,
            trailingIcon = {
                IconToggleButton(checked = expanded, onCheckedChange = { expanded = it }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Menu"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth()) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onItemSelected(item)
                }) {
                    Text(text = item, style = textStyle)
                }
            }
        }
    }
}

@Composable
fun DatePickerComponent(onDateChange: (String) -> Unit) {
    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val currentDate = Calendar.getInstance()
    val initialDate = remember { mutableStateOf(currentDate.time) }
    val selectedDate = remember { mutableStateOf(currentDate.time) }

    val dateString = remember(selectedDate.value) { dateFormatter.format(selectedDate.value) }

    Box {
        OutlinedTextField(
            value = dateString,
            onValueChange = {},
            label = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val calendar = Calendar.getInstance()
                            calendar.set(year, month, dayOfMonth)
                            selectedDate.value = calendar.time
                            onDateChange(dateString)
                        },
                        currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH),
                        currentDate.get(Calendar.DAY_OF_MONTH)
                    )
                    datePickerDialog.show()
                }) {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date Picker")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}