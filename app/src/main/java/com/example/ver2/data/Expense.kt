package com.example.ver2.data

data class Expense(
    val id: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val date: String = "",
    val description: String = "",
    val paymentMode: String = "",
    val sharedWith: List<String> = listOf()
)