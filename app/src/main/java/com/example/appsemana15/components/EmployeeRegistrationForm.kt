package com.example.appsemana15.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appsemana15.data.entities.EmployeeEntity
import com.example.appsemana15.viewmodels.EmployeeViewModel

@Composable
fun EmployeeRegistrationForm(onClose: () -> Unit, viewModel: EmployeeViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombre") },
            singleLine = true
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            singleLine = true
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Cargo") },
            singleLine = true
        )

        OutlinedTextField(
            value = salary,
            onValueChange = { salary = it },
            label = { Text("Salario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val employee = EmployeeEntity(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    position = position,
                    salary = salary.toDouble()
                )

                viewModel.saveEmployee(employee)

                onClose()
            }) {
                Text("Registrar")
            }
            TextButton(onClick = { onClose() }) {
                Text("Cancelar")
            }
        }
    }
}