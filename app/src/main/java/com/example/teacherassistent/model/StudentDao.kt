package com.example.teacherassistent.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table")
    fun allStudents(): LiveData<List<Student>>

    @Insert
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Update
    suspend fun update(student: Student)

    @Query("SELECT * FROM student_table where semester = :semester")
    fun filteredStudents(semester: Int): LiveData<List<Student>>
}