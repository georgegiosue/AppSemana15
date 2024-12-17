package com.example.appsemana15.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appsemana15.data.dao.EmployeeDao
import com.example.appsemana15.data.entities.EmployeeEntity

@Database(
    entities = [EmployeeEntity::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val employeeDao: EmployeeDao
}