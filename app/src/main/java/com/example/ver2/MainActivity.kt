package com.example.ver2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ver2.data.Expense
import com.example.ver2.data.SharedExpense
import com.example.ver2.ui.screens.*
import com.example.ver2.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<AuthViewModel>()
            val auth: FirebaseAuth = Firebase.auth
            val user = auth.currentUser
            val userId = user?.uid
            val expenses = remember { mutableStateOf(listOf<Expense>()) }
            val screen = remember {
                mutableStateOf(if (viewModel.isLoggedIn()) "dashboard" else "login")
            }
            val selectedExpense = remember { mutableStateOf<Expense?>(null) }
            val showEditExpenseScreen = remember { mutableStateOf(false) }
            val showDeleteConfirmationDialog = remember { mutableStateOf(false) }
            val totalSharedBalance = remember { mutableStateOf("0") }

            Column(modifier = Modifier.fillMaxSize()) {
                when (screen.value) {
                    "login" -> LoginScreen(
                        onNavigateToRegister = { screen.value = "register" },
                        onNavigateToForgotPassword = { screen.value = "forgotPassword" },
                        onLoginClick = { email, password, navigateToDashboard ->
                            viewModel.login(email, password, navigateToDashboard)
                        },
                        navigateToDashboard = { screen.value = "dashboard" }
                    )

                    "register" -> RegisterScreen(
                        onNavigateBack = { screen.value = "login" },
                        onRegisterClick = { email, password, showMessage, navigateToLogin ->
                            viewModel.register(email, password, showMessage, navigateToLogin)
                        },
                        navigateToLogin = { screen.value = "login" }
                    )

                    "forgotPassword" -> ForgotPasswordScreen(
                        onNavigateBack = { screen.value = "login" },
                        onResetPasswordClick = { email, showMessage ->
                            viewModel.resetPassword(email, showMessage)
                        }
                    )

                    "dashboard" -> DashboardScreen(
                        navigateToSettings = { screen.value = "settings" },
                        navigateToSplitExpense = { screen.value = "splitExpense" }, // Add this line
                        navigateToViewSharedExpenses = { }, // Add this line
                        navigateToReports = { },
                        totalExpense = expenses.value.sumByDouble { it.amount }.toString(),
                        totalSharedBalance = totalSharedBalance.value, // Add this line
                        onAddExpenseClick = { screen.value = "addExpense" },
                        onViewExpenseClick = { screen.value = "viewExpenses"; fetchExpenses(userId, expenses)}
                    )


                    "settings" -> SettingsScreen(
                        onBackClick = { screen.value = "dashboard" },
                        onProfileClick = { screen.value = "profile" },
                        onLogoutClick = {
                            viewModel.signOut()
                            screen.value = "login"
                        },
                        onAboutClick = { screen.value = "about" } // Add this line
                    )

                    "profile" -> ProfileScreen(
                        onBackClick = { screen.value = "settings" },
                        onSettingsBackClick = { screen.value = "dashboard" }
                    )

                    "addExpense" -> AddExpenseScreen(
                        onNavigateBack = { screen.value = "dashboard" },
                        onAddExpenseClick = { expense, onSuccess, onError ->
                            if (userId != null) {
                                saveExpenseToFirebase(userId, expense, onSuccess, onError)
                            }
                        })
                    "about" -> AboutScreen(
                        onBackClick = { screen.value = "settings" })

                    "viewExpenses" -> ViewExpensesScreen(
                        userId = userId ?: "",
                        onNavigateBack = { screen.value = "dashboard" },
                        expenses = expenses.value,
                        totalExpense = expenses.value.sumByDouble { it.amount }.toString()
                    )

                    "splitExpense" -> SplitExpenseScreen(
                        onNavigateBack = { screen.value = "dashboard" },
                        onSaveSharedExpenseClick = { sharedExpense ->
                            if (userId != null) {
                                saveSharedExpenseToFirebase(userId, sharedExpense)
                            }
                        }
                    )

                    "viewSharedExpenses" -> ViewSharedExpensesScreen(
                        // Add necessary parameters and callbacks for the ViewSharedExpensesScreen
                        onNavigateBack = { screen.value = "dashboard" }
                    )


                }
            }
        }
    }

    private fun saveExpenseToFirebase(
        userId: String,
        expense: Expense,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {


        val database = FirebaseDatabase.getInstance().reference
        val expenseId = userId?.let { database.child("expenses").child(it).push().key } ?: ""

        database.child("expenses").child(userId).child(expenseId)
            .setValue(expense.copy(id = expenseId))
            .addOnSuccessListener {
                Log.d("MainActivity", "Expense added successfully")
                onSuccess("Expense added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Failed to add expense: ${exception.localizedMessage}")
                onError("Failed to add expense: ${exception.localizedMessage}")
            }
    }

    private fun fetchExpenses(
        userId: String?,
        expenses: MutableState<List<Expense>>
    ) {
        if (userId == null) return

        val database = FirebaseDatabase.getInstance().reference
        database.child("expenses").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val fetchedExpenses = dataSnapshot.children.mapNotNull { snapshot ->
                        snapshot.getValue(Expense::class.java)
                    }
                    expenses.value = fetchedExpenses
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainActivity", "Failed to fetch expenses: ${error.message}")
                }
            })
    }
    private fun deleteExpenseFromFirebase(
        userId: String,
        expenseId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val database = FirebaseDatabase.getInstance().reference
        database.child("expenses").child(userId).child(expenseId)
            .removeValue()
            .addOnSuccessListener {
                Log.d("MainActivity", "Expense deleted successfully")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Failed to delete expense: ${exception.localizedMessage}")
                onError("Failed to delete expense: ${exception.localizedMessage}")
            }
    }

    private fun saveSharedExpenseToFirebase(
        currentUserId: String,
        sharedExpense: SharedExpense
    ) {
        val database = FirebaseDatabase.getInstance().reference
        val sharedExpenseId = database.child("sharedExpenses").push().key ?: ""

        val sharedExpenseWithId = sharedExpense.copy(id = sharedExpenseId)

        val updates = mutableMapOf<String, Any>()
        updates["/sharedExpenses/$sharedExpenseId"] = sharedExpenseWithId

        sharedExpense.participants.forEach { participant ->
            updates["/userSharedExpenses/${participant.userId}/$sharedExpenseId"] = sharedExpenseWithId
        }

        database.updateChildren(updates)
            .addOnSuccessListener {
                Log.d("MainActivity", "Shared expense added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Failed to add shared expense: ${exception.localizedMessage}")
            }
    }

}
