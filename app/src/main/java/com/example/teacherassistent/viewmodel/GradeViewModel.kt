package com.example.teacherassistent.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.teacherassistent.model.*
import com.example.teacherassistent.model.repository.CourseRepository
import com.example.teacherassistent.model.repository.GradeRepository
import com.example.teacherassistent.model.repository.StudentCourseRepository
import com.example.teacherassistent.model.repository.StudentRepository
import kotlinx.coroutines.launch

class GradeViewModel(application: Application): AndroidViewModel(application) {
    //var grades: LiveData<List<Grade>>
    var currentGrade: Double = 0.0

    private val currentDate = MutableLiveData<String>()
    val todayGrades: LiveData<List<StudentGrade>> = Transformations.switchMap(currentDate)
    {
        currentDate -> gradeRepository.getTmp(currentDate)
    }

    /*
    val todayGrades: LiveData<List<Grade>> = Transformations.switchMap(currentDate)
    {
        currentDate -> gradeRepository.getTodayGrades(currentDate)
    }*/

    private val inputStudentCourseId = MutableLiveData<Int>()
    val grades: LiveData<List<Grade>> = Transformations.switchMap(inputStudentCourseId)
    {
        inputStudentCourseId -> gradeRepository.getGrades(inputStudentCourseId)
    }

    private val gradeRepository: GradeRepository

    init {
        gradeRepository = GradeRepository(DataBase.getDatabase(application).gradeDao())
    }

    fun addGrade(studentCourseId: Int, grade: Double, comment: String, date: String)
    {
        if (studentCourseId != -1)
        {
            viewModelScope.launch {
                gradeRepository.add(Grade(id = 0, studentCourseId = studentCourseId, grade = grade, comment = comment,  date = date))
            }
        }
    }

    fun getGrades(studentCourseId: Int?)
    {
        if(studentCourseId != null)
        {
            inputStudentCourseId.value = studentCourseId
        }
    }

    fun getTodayGrades(date: String)
    {
        currentDate.value = date
    }

    fun deleteGrade(grade: Grade?)
    {
        if (grade != null)
        {
            viewModelScope.launch {
                gradeRepository.delete(grade)
            }
        }
    }
}