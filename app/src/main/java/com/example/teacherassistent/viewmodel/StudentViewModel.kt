package com.example.teacherassistent.viewmodel

import android.app.Application
import android.database.Observable
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.example.teacherassistent.model.DataBase
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.model.StudentCourse
import com.example.teacherassistent.model.repository.StudentCourseRepository
import com.example.teacherassistent.model.repository.StudentRepository
import com.example.teacherassistent.view.addingStudent.AddingStudentFragment
import kotlinx.coroutines.launch

class StudentViewModel(application: Application): AndroidViewModel(application) {
    var currentStudent: Student? = null

    private val input = MutableLiveData<Int>()

    val students: LiveData<List<Student>> = Transformations.switchMap(input)
    {
        input -> studentRepository.filteredStudent(input)
    }

    private val studentRepository: StudentRepository

    init {
        studentRepository = StudentRepository(DataBase.getDatabase(application).studentDao())
    }

    fun addStudent(name: String, surname: String, semester: Int)
    {
        if(name.length > 0 && surname.length > 0)
        {
            viewModelScope.launch {
                studentRepository.add(Student(name = name, surname = surname, semester = semester, id = 0))
            }
        }
    }

    fun filteredStudents(semester: Int)
    {
        input.value = semester
        //students = studentRepository.filteredStudent(semester)
    }

    fun deleteStudent(student: Student?)
    {
        if (student != null)
        {
            viewModelScope.launch {
                studentRepository.delete(student)
            }
        }
    }

    fun update(student: Student?)
    {
        if (student != null)
        {
            viewModelScope.launch {
                println(student)
                studentRepository.update(student)
            }
        }
    }





}