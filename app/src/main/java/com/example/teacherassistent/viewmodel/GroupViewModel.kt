package com.example.teacherassistent.viewmodel

import android.app.Application
import android.security.keystore.StrongBoxUnavailableException
import androidx.lifecycle.*
import com.example.teacherassistent.model.Course
import com.example.teacherassistent.model.DataBase
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.model.StudentCourse
import com.example.teacherassistent.model.repository.CourseRepository
import com.example.teacherassistent.model.repository.StudentCourseRepository
import com.example.teacherassistent.model.repository.StudentRepository
import kotlinx.coroutines.launch

class GroupViewModel(application: Application): AndroidViewModel(application) {
    //var group: LiveData<List<Student>>
    //var course: LiveData<List<Course>>
    //var currentGroup: Int = -1
    var currentStudent: Student? = null
    var currentCourse: Course? = null

    private val myCourseId = MutableLiveData<Int>()
    private val myStudentId = MutableLiveData<Int>()

    init {
        myCourseId.value = -1
        myStudentId.value = -1
    }

    val combinedValues = MediatorLiveData<Pair<Int?, Int?>>().apply {
        addSource(myCourseId) {
            value = Pair(it, myStudentId.value)
        }
        addSource(myStudentId) {
            value = Pair(myCourseId.value, it)
        }
    }

    val currentGroup: LiveData<Int> = Transformations.switchMap(combinedValues) {  pair ->
        val first = pair.first
        val second = pair.second

        if(first != null && second != null)
        {
            groupRepository.getCurrentId(first, second)
        }
        else
        {
            null
        }
    }

    private val inputCourseId = MutableLiveData<Int>()
    val group: LiveData<List<Student>> = Transformations.switchMap(inputCourseId)
    {
        inputCourseId -> groupRepository.getGroups(inputCourseId)
    }

    private val inputStudentId = MutableLiveData<Int>()
    val courses: LiveData<List<Course>> = Transformations.switchMap(inputStudentId)
    {
        inputStudentId -> groupRepository.getCourses(inputStudentId)
    }

    private val groupRepository: StudentCourseRepository

    init {
        groupRepository = StudentCourseRepository(DataBase.getDatabase(application).studentCourseDao())
        //group = groupRepository.getGroups(1)
        //course = groupRepository.getCourses(-1)
    }

    fun filteredGroup(courseId: Int)
    {
        inputCourseId.value = courseId
    }

    fun getCoursesForStudent(studentId: Int?)
    {
        if (studentId != null)
        {
            inputStudentId.value = studentId
        }
    }

    fun getCurrentStudentCourse(courseId: Int?, studentId: Int?)
    {
        if(courseId != null && studentId != null)
        {
            myCourseId.value = courseId
            myStudentId.value = studentId
        }
    }

    fun addGroup(studentId: Int?, courseId: Int?)
    {
        if (studentId != null && courseId != null)
        {
            viewModelScope.launch {
                groupRepository.add(StudentCourse(id = 0, student_id = studentId, course_id = courseId))
            }
        }
    }

    fun deleteGroup(studentCourse: StudentCourse)
    {
        viewModelScope.launch {
            groupRepository.delete(studentCourse)
        }
    }
}