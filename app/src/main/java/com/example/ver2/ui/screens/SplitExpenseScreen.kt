package com.example.ver2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import com.example.ver2.data.Participant
import com.example.ver2.data.SharedExpense

@Composable
fun SplitExpenseScreen(
    onNavigateBack: () -> Unit,
    onSaveSharedExpenseClick: (SharedExpense) -> Unit
) {
    val title = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val totalAmount = remember { mutableStateOf("") }
    val splitType = remember { mutableStateOf("equal") }
    val participants = remember { mutableStateListOf<Participant>() }
    val inputError = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Split Expense",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description.value,
            onValueChange = { description.value = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = totalAmount.value,
            onValueChange = { totalAmount.value = it },
            label = { Text("Total Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Split Type", style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            RadioButton(
                selected = splitType.value == "equal",
                onClick = { splitType.value = "equal" }
            )
            Text("Equal", style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = splitType.value == "custom",
                onClick = { splitType.value = "custom" }
            )
            Text("Custom", style = MaterialTheme.typography.body1)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add and manage participant composable here
        // ...

        if (inputError.value) {
            Text("Please fill in all fields and add at least one participant.", color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    if (title.value.isNotEmpty() && category.value.isNotEmpty() &&
                        description.value.isNotEmpty() && totalAmount.value.isNotEmpty() &&
                        participants.isNotEmpty()
                    ) {
                        val sharedExpense = SharedExpense(
                            title = title.value,
                            category = category.value,
                            description = description.value,
                            totalAmount = totalAmount.value.toDoubleOrNull() ?: 0.0,
                            splitType = splitType.value,
                            participants = participants
                        )
                        onSaveSharedExpenseClick(sharedExpense)
                        onNavigateBack()
                    } else {
                        inputError.value = true
                    }
                }
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { onNavigateBack() }) {
                Text("Cancel")
            }
        }
    }
}