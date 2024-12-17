package com.example.appsemana15.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appsemana15.data.entities.EmployeeEntity
import com.example.appsemana15.viewmodels.EmployeeViewModel

@Composable
fun EmployeeListScreen(employeeList: List<EmployeeEntity>, viewModel: EmployeeViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredEmployees = employeeList.filter {
        it.firstName.contains(searchQuery, ignoreCase = true) ||
                it.lastName.contains(searchQuery, ignoreCase = true) ||
                it.position.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        SearchBar(searchQuery) { query ->
            searchQuery = query
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredEmployees.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No se encontraron empleados",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filteredEmployees) { employee ->
                    EmployeeListItem(employee, viewModel)
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchQuery: String, onQueryChanged: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChanged,
        label = { Text("Buscar empleado") },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Outlined.Search, contentDescription = "Buscar empleado")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EmployeeListItem(employee: EmployeeEntity, viewModel: EmployeeViewModel) {

    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${employee.firstName} ${employee.lastName}", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cargo: ${employee.position}", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = "Email: ${employee.email}", fontSize = 16.sp, color = Color.DarkGray)
            Text(text = "Salario: S/.${employee.salary}", fontSize = 16.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showUpdateDialog = true }) {
                Icon(Icons.Outlined.Edit, contentDescription = "Editar empleado")
                Text(text = "Editar", modifier = Modifier.padding(start = 4.dp))
            }
            Button(onClick = { showDeleteDialog = true }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Icon(Icons.Outlined.Delete, contentDescription = "Eliminar empleado")
                Text(text = "Eliminar", modifier = Modifier.padding(start = 4.dp))
            }
        }
    }

    if (showUpdateDialog) {
        UpdateEmployeeDialog(employee, {
            viewModel.updateEmployee(it)
        }) {
            showUpdateDialog = false
        }
    }

    if (showDeleteDialog) {
        DeleteEmployeeDialog(employee, {
            viewModel.deleteEmployee(it)
        }) {
            showDeleteDialog = false
        }
    }
}
