package com.example.appsemana15.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appsemana15.data.entities.EmployeeEntity


@Composable
fun UpdateEmployeeDialog(
    employee: EmployeeEntity,
    onUpdateEmployee: (EmployeeEntity) -> Unit,
    onDismiss: () -> Unit
) {
    var firstName by remember { mutableStateOf(employee.firstName) }
    var lastName by remember { mutableStateOf(employee.lastName) }
    var email by remember { mutableStateOf(employee.email) }
    var position by remember { mutableStateOf(employee.position) }
    var salary by remember { mutableStateOf(employee.salary.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Actualizar Empleado") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("Cargo") })
                OutlinedTextField(
                    value = salary,
                    onValueChange = { salary = it },
                    label = { Text("Salario") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedEmployee = employee.copy(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    position = position,
                    salary = salary.toDoubleOrNull() ?: employee.salary
                )
                onUpdateEmployee(updatedEmployee)
                onDismiss()
            }) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
