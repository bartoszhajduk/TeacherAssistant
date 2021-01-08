package com.example.teacherassistent.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.model.StudentDao
import kotlinx.coroutines.delay

class StudentRepository(private val studentDao: StudentDao) {

    fun readAll() = liveData {
        emitSource(studentDao.allStudents())
    }

    suspend fun add(student: Student) {
        studentDao.insert(student)
    }

    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

    suspend fun update(student: Student){
        studentDao.update(student)
    }

    fun filteredStudent(semester: Int) = liveData {
        emitSource(studentDao.filteredStudents(semester))
    }


}