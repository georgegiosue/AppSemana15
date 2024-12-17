package com.example.appsemana15

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appsemana15.components.EmployeeListScreen
import com.example.appsemana15.components.EmployeeRegistrationForm
import com.example.appsemana15.data.Database
import com.example.appsemana15.ui.theme.AppSemana15Theme
import com.example.appsemana15.viewmodels.EmployeeViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "appsemana15"
        ).build()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var showDialog by remember { mutableStateOf(false) }

            val employeeViewModel by viewModels<EmployeeViewModel>(
                factoryProducer = {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return EmployeeViewModel(db.employeeDao) as T
                        }
                    }
                }
            )

            val employees = employeeViewModel.getEmployees().collectAsState(null)

            AppSemana15Theme {
                Scaffold(
                    topBar = {
                        TopAppBar(

                            title = {
                                Text(
                                    text = "Empleado App",
                                    style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                        )
                    },
                    floatingActionButton = {
                        FilledTonalButton(onClick = {showDialog = true}, modifier = Modifier.padding(4.dp)) {
                            Text("Nuevo empleado", style = TextStyle(fontSize = 16.sp))
                            Icon(Icons.Outlined.Add, contentDescription = "Add")
                        }
                    }
                ) { innerPadding ->

                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                    ) {
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                confirmButton = {},
                                text = {
                                    EmployeeRegistrationForm(onClose = { showDialog = false }, viewModel = employeeViewModel)
                                },
                                title = {
                                    Text(text = "Registro de Empleados")
                                }
                            )
                        }

                        EmployeeListScreen(employeeList = employees.value ?: emptyList(), viewModel = employeeViewModel)
                    }
                }
            }
        }
    }
}