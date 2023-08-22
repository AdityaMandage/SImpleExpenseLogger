package com.example.ver2.data

data class SharedExpense(
    val id: String = "",
    val title: String = "",
    val category: String = "",
    val description: String = "",
    val date: Long = 0L,
    val totalAmount: Double = 0.0,
    val splitType: String = "",
    val participants: List<Participant> = emptyList()
)

data class Participant(
    val userId: String = "",
    val displayName: String = "",
    val amount: Double = 0.0
)