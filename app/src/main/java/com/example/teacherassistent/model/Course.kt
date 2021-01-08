package com.example.teacherassistent.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class Course(@PrimaryKey(autoGenerate = true) val id: Int, var nameOfCourse: String, var semester: Int)
{
    override fun toString(): String {
        return nameOfCourse
    }
}
