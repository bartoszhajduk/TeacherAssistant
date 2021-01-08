package com.example.teacherassistent.model.repository

import androidx.lifecycle.liveData
import com.example.teacherassistent.model.Course
import com.example.teacherassistent.model.Grade
import com.example.teacherassistent.model.GradeDao

class GradeRepository(private val gradeDao: GradeDao) {

    fun getGrades(studentCourseId: Int) = liveData {
        emitSource(gradeDao.getGrades(studentCourseId))
    }

    fun getTodayGrades(date: String) = liveData {
        emitSource(gradeDao.getTodayGrades(date))
    }

    fun getTmp(date: String) = liveData {
        emitSource(gradeDao.tmp(date))
    }

    suspend fun add(grade: Grade) {
        gradeDao.insert(grade)
    }

    suspend fun delete(grade: Grade) {
        gradeDao.delete(grade)
    }
}

    /*

    fun getGrades(studentCourseId: Int) = liveData {
        emitSource(studentCourseDao.getCurrentCourses(studentId))
    }

    fun readAll() = liveData {
        emitSource(courseDao.allCourses())
    }



    suspend fun delete(Course: Course) {
        courseDao.delete(Course)
    }

    suspend fun update(Course: Course){
        courseDao.update(Course)
    }

    fun filteredCourses(semester: Int) = liveData {
        emitSource(courseDao.filteredCourses(semester))
    }
*/
