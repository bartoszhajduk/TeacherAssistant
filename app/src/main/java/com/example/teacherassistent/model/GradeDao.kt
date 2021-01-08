package com.example.teacherassistent.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GradeDao {
    @Query("SELECT * FROM grade_table where studentCourseId = :studentCourseId")
    fun getGrades(studentCourseId: Int): LiveData<List<Grade>>

    @Query("SELECT * FROM grade_table where date = :date")
    fun getTodayGrades(date: String): LiveData<List<Grade>>

    @Query("SELECT students.name, students.surname, courses.nameOfCourse, grades.grade, grades.comment, grades.date FROM grade_table as grades, student_table as students, course_table as courses, student_course_table as joined where date = :date and (joined.course_id = courses.id and joined.student_id = students.id and joined.id = grades.studentCourseId)")
    fun tmp(date: String): LiveData<List<StudentGrade>>

    @Insert
    suspend fun insert(grade: Grade)

    @Delete
    suspend fun delete(grade: Grade)

    /*
    @Query("SELECT * FROM student_table where semester = :semester")
    fun filteredStudents(semester: Int): LiveData<List<Student>>
    */
}