package com.example.appsemana15.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.appsemana15.data.entities.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee ORDER BY lastName ASC")
    fun getAllEmployeeByLastNameAsc(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM employee WHERE lastName LIKE :lastName")
    fun findByLastName(lastName: String): EmployeeEntity

    @Upsert
    suspend fun upsert(employeeEntity: EmployeeEntity)

    @Insert
    suspend fun insertAll(vararg employeeEntity: EmployeeEntity)

    @Delete
    fun delete(employeeEntity: EmployeeEntity)

    // update employee
    @Query("UPDATE employee SET firstName = :firstName, lastName = :lastName, email = :email, position = :position, salary = :salary WHERE id = :id")
    fun updateEmployee(id: Int, firstName: String, lastName: String, email: String, position: String, salary: Double)
}
