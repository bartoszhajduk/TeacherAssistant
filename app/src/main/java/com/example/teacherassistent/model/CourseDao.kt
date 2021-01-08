package com.example.teacherassistent.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CourseDao {
    @Query("SELECT * FROM course_table")
    fun allCourses(): LiveData<List<Course>>

    @Insert
    suspend fun insert(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Update
    suspend fun update(course: Course)

    @Query("SELECT * FROM course_table where semester = :semester")
    fun filteredCourses(semester: Int): LiveData<List<Course>>
}