package com.example.appsemana15.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsemana15.data.dao.EmployeeDao
import com.example.appsemana15.data.entities.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel(private val dao: EmployeeDao) : ViewModel() {

    fun saveEmployee(employeeEntity: EmployeeEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.upsert(employeeEntity)
            }
        }
    }

    fun getEmployees() = dao.getAllEmployeeByLastNameAsc()

    fun updateEmployee(employeeEntity: EmployeeEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.updateEmployee(employeeEntity.id, employeeEntity.firstName, employeeEntity.lastName, employeeEntity.email, employeeEntity.position, employeeEntity.salary)
            }
        }
    }

    fun deleteEmployee(employeeEntity: EmployeeEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                dao.delete(employeeEntity)
            }
        }
    }
}