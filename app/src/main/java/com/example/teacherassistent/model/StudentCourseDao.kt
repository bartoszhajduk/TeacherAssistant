package com.example.teacherassistent.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StudentCourseDao {
    @Query("SELECT students.* FROM student_course_table as joined, student_table as students where (joined.course_id = :courseId) and (joined.student_id = students.id)")
    fun getGroups(courseId: Int): LiveData<List<Student>>

    @Query("SELECT courses.* FROM student_course_table as joined, course_table as courses where (joined.student_id = :studentId) and (joined.course_id = courses.id)")
    fun getCurrentCourses(studentId: Int): LiveData<List<Course>>

    @Query("SELECT id FROM student_course_table where (course_id = :courseId) and (student_id = :studentId)")
    fun getCurrentStudentCourse(courseId: Int, studentId: Int): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(studentCourse: StudentCourse)

    @Delete
    suspend fun delete(studentCourse: StudentCourse)
}