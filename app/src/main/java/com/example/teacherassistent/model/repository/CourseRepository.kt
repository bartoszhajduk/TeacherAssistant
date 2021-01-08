package com.example.teacherassistent.model.repository

import androidx.lifecycle.liveData
import com.example.teacherassistent.model.CourseDao
import com.example.teacherassistent.model.Course

class CourseRepository(private val courseDao: CourseDao) {

    fun readAll() = liveData {
        emitSource(courseDao.allCourses())
    }

    suspend fun add(Course: Course) {
        courseDao.insert(Course)
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

}