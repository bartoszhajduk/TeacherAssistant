package com.example.teacherassistent.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "grade_table")
data class Grade(@PrimaryKey(autoGenerate = true)
                 val id: Int,
                 var studentCourseId: Int,
                 var grade: Double,
                 var comment: String,
                 var date: String)