package com.example.teacherassistent.view.addingGrade


import android.os.Build
import java.time.LocalDateTime
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R
import com.example.teacherassistent.model.Course
import com.example.teacherassistent.model.Grade
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.viewmodel.*
import kotlinx.android.synthetic.main.fragment_adding_student.*
import kotlinx.android.synthetic.main.fragment_adding_student_course.*
import kotlinx.android.synthetic.main.fragment_grade.*
import kotlinx.android.synthetic.main.grade_list_row.*
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue


class AddingGradeFragment : Fragment() {
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var gradeViewModel: GradeViewModel

    private lateinit var spinnerStudentsAdapter: ArrayAdapter<Student>
    private lateinit var spinnerCoursesAdapter: ArrayAdapter<Course>

    private lateinit var gradesLayoutManager: LinearLayoutManager
    private lateinit var gradeListAdapter: GradeListAdapter
    private lateinit var gradesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentViewModel = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
        groupViewModel = ViewModelProvider(requireActivity()).get(GroupViewModel::class.java)
        gradeViewModel = ViewModelProvider(requireActivity()).get(GradeViewModel::class.java)

        gradesLayoutManager = LinearLayoutManager(context)
        gradeListAdapter = GradeListAdapter(gradeViewModel)

        studentViewModel.students.observe(viewLifecycleOwner, { spinnerData ->
            spinnerStudentsAdapter = ArrayAdapter(view?.context!!, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            grade_student_spinner.adapter = spinnerStudentsAdapter
        })

        groupViewModel.courses.observe(viewLifecycleOwner, { spinnerData ->
            spinnerCoursesAdapter = ArrayAdapter(view?.context!!, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            grade_course_spinner.adapter = spinnerCoursesAdapter
        })

        groupViewModel.currentGroup.observe(viewLifecycleOwner, {
            gradeViewModel.getGrades(groupViewModel.currentGroup.value)
        })

        gradeViewModel.grades.observe(viewLifecycleOwner, {
            gradeListAdapter.notifyDataSetChanged()
        })

//        groupViewModel.combinedValues.observe(viewLifecycleOwner, {
//            gradeListAdapter.notifyDataSetChanged()
//        })


//        groupViewModel.currentGroup.observe(viewLifecycleOwner, {
//            gradeListAdapter.notifyDataSetChanged()
//            //gradeListAdapter.notifyDataSetChanged()
//        })


        val root = inflater.inflate(R.layout.fragment_grade, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gradesRecyclerView = grade_recycler.apply {
            this.layoutManager = gradesLayoutManager
            this.adapter = gradeListAdapter
        }

        grade_semester_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                studentViewModel.filteredStudents(position + 1)
                groupViewModel.getCoursesForStudent(-1)
                gradeViewModel.getGrades(-1)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        grade_student_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                groupViewModel.getCurrentStudentCourse(groupViewModel.currentCourse?.id, spinnerStudentsAdapter.getItem(position)?.id)

                groupViewModel.currentStudent = spinnerStudentsAdapter.getItem(position)
                groupViewModel.getCoursesForStudent(spinnerStudentsAdapter.getItem(position)?.id)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        grade_course_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                groupViewModel.getCurrentStudentCourse(spinnerCoursesAdapter.getItem(position)?.id, groupViewModel.currentStudent?.id)

                groupViewModel.currentCourse = spinnerCoursesAdapter.getItem(position)
                gradeViewModel.getGrades(groupViewModel.currentGroup.value)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        grade_grade_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gradeViewModel.currentGrade = grade_grade_spinner.selectedItem.toString().toDouble()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        grade_button_add.setOnClickListener {
            if(groupViewModel.currentGroup.value != null)
            {
                gradeViewModel.addGrade(groupViewModel.currentGroup.value!!, gradeViewModel.currentGrade, grade_comment.text.toString(), SimpleDateFormat("dd/yyyy").format(Date()))
            }
        }
    }
}