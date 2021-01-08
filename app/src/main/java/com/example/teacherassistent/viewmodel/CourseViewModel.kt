package com.example.teacherassistent.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.teacherassistent.model.Course
import com.example.teacherassistent.model.DataBase
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.model.repository.CourseRepository
import com.example.teacherassistent.model.repository.StudentRepository
import kotlinx.coroutines.launch

class CourseViewModel(application: Application): AndroidViewModel(application) {
    //var courses: LiveData<List<Course>>

    var currentCourse: Course? = null

    private val input = MutableLiveData<Int>()

    val courses: LiveData<List<Course>> = Transformations.switchMap(input)
    {
        input -> courseRepository.filteredCourses(input)
    }

    private val courseRepository: CourseRepository

    init {
        courseRepository = CourseRepository(DataBase.getDatabase(application).courseDao())
        //courses = courseRepository.readAll()
    }

    fun filteredCourses(semester: Int)
    {
        input.value = semester
        //courses = courseRepository.filteredCourses(semester)
    }

    fun addCourse(name: String, semester: Int)
    {
        if(name.length > 0)
        {
            viewModelScope.launch {
                courseRepository.add(Course(nameOfCourse = name, semester = semester, id = 0))
            }
        }
    }

    fun deleteCourse(course: Course?)
    {
        if (course != null)
        {
            viewModelScope.launch {
                courseRepository.delete(course)
            }
        }
    }

    fun update(course: Course?)
    {
        if (course != null)
        {
            viewModelScope.launch {
                courseRepository.update(course)
            }
        }
    }


}