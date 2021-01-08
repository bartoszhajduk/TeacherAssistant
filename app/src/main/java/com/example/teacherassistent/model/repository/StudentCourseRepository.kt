package com.example.teacherassistent.model.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.model.StudentCourse

import com.example.teacherassistent.model.StudentCourseDao

class StudentCourseRepository(private val studentCourseDao: StudentCourseDao) {

    fun getGroups(groupId: Int) = liveData {
        emitSource(studentCourseDao.getGroups(groupId))
    }

    fun getCourses(studentId: Int) = liveData {
        emitSource(studentCourseDao.getCurrentCourses(studentId))
    }

    fun getCurrentId(courseId: Int, studentId: Int) = liveData {
        emitSource(studentCourseDao.getCurrentStudentCourse(courseId, studentId))
    }

    suspend fun add(studentCourse: StudentCourse) {
        studentCourseDao.insert(studentCourse)
    }

    suspend fun delete(studentCourse: StudentCourse) {
        studentCourseDao.delete(studentCourse)
    }
}