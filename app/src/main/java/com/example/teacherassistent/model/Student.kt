package com.example.teacherassistent.model


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "student_table")
data class Student(@PrimaryKey(autoGenerate = true) val id: Int, var name: String, var surname: String, var semester: Int)
{
    override fun toString(): String {
        return name+" "+surname
    }
}