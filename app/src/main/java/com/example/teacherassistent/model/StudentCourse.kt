package com.example.teacherassistent.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.zip.CheckedOutputStream

@Entity(tableName = "student_course_table",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["student_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = [ "student_id", "course_id" ], unique = true)]

)

data class StudentCourse(@PrimaryKey(autoGenerate = true)
                         val id: Int,
                         val student_id: Int,
                         val course_id: Int)
