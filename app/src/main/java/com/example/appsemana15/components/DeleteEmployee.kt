package com.example.appsemana15.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.appsemana15.data.entities.EmployeeEntity

@Composable
fun DeleteEmployeeDialog(
    employee: EmployeeEntity,
    onDeleteEmployee: (EmployeeEntity) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Eliminar Empleado") },
        text = { Text("¿Está seguro de que desea eliminar a ${employee.firstName} ${employee.lastName}?") },
        confirmButton = {
            Button(onClick = {
                onDeleteEmployee(employee)
                onDismiss()
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}