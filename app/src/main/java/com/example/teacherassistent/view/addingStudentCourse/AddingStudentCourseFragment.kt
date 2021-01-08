package com.example.teacherassistent.view.addingStudentCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R
import com.example.teacherassistent.model.Course
import com.example.teacherassistent.model.StudentCourse
import com.example.teacherassistent.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_adding_student_course.*


class AddingStudentCourseFragment : Fragment() {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var courseViewModel: CourseViewModel
    private lateinit var groupViewModel: GroupViewModel

    private lateinit var studentCourseListAdapter: StudentCourseListAdapter
    private lateinit var studentCourseListAdapterSecond: StudentCourseListAdapterSecond

    private lateinit var studentLayoutManager: LinearLayoutManager
    private lateinit var studentLayoutManagerSecond: LinearLayoutManager

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var studentRecyclerViewSecond: RecyclerView

    private lateinit var spinnerAdapter: ArrayAdapter<Course>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        studentLayoutManager = LinearLayoutManager(context)
        studentLayoutManagerSecond = LinearLayoutManager(context)

        studentViewModel = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
        courseViewModel = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
        groupViewModel = ViewModelProvider(requireActivity()).get(GroupViewModel::class.java)

        studentCourseListAdapter = StudentCourseListAdapter(studentViewModel)
        studentCourseListAdapterSecond = StudentCourseListAdapterSecond(groupViewModel, courseViewModel)

        studentViewModel.students.observe(viewLifecycleOwner, {
            studentCourseListAdapter.notifyDataSetChanged()
        })

        courseViewModel.courses.observe(viewLifecycleOwner, { spinnerData ->
            spinnerAdapter = ArrayAdapter(view?.context!!, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            student_course_name_spinner.adapter = spinnerAdapter
        })

        groupViewModel.group.observe(viewLifecycleOwner, {
            studentCourseListAdapterSecond.notifyDataSetChanged()
        })

        groupViewModel.currentGroup.observe(viewLifecycleOwner, {
            studentCourseListAdapterSecond.notifyDataSetChanged()
        })

//        groupViewModel.combinedValues.observe(viewLifecycleOwner, {
//            studentCourseListAdapterSecond.notifyDataSetChanged()
//        })

        val root = inflater.inflate(R.layout.fragment_adding_student_course, main_container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentRecyclerView = student_course_recycler_1.apply {
            this.layoutManager = studentLayoutManager
            this.adapter = studentCourseListAdapter
        }

        studentRecyclerViewSecond = student_course_recycler_2.apply {
            this.layoutManager = studentLayoutManagerSecond
            this.adapter = studentCourseListAdapterSecond
        }

        student_course_input_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                studentViewModel.filteredStudents(position + 1)
                courseViewModel.filteredCourses(position + 1)
                groupViewModel.filteredGroup(-1)

                studentViewModel.currentStudent = null
                courseViewModel.currentCourse = null
                student_course_button_delete.isEnabled = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        student_course_name_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                courseViewModel.currentCourse = spinnerAdapter.getItem(position)!!
                groupViewModel.filteredGroup(courseViewModel.currentCourse?.id!!)

                studentViewModel.currentStudent = null
                groupViewModel.currentStudent = null
                //groupViewModel.currentGroup = -1
                student_course_button_delete.isEnabled = false
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        student_course_button_add.setOnClickListener {
            if (studentViewModel.currentStudent?.id != null && courseViewModel.currentCourse?.id != null) {
                groupViewModel.addGroup(studentViewModel.currentStudent?.id, courseViewModel.currentCourse?.id)
            }
        }

        student_course_button_delete.setOnClickListener {
            if (/*groupViewModel.currentGroup != -1*/  groupViewModel.currentStudent?.id != null && courseViewModel.currentCourse?.id != null) {
                groupViewModel.deleteGroup(StudentCourse(groupViewModel.currentGroup.value!!, groupViewModel.currentStudent?.id!!, courseViewModel.currentCourse?.id!!))
                //groupViewModel.currentGroup = -1
                groupViewModel.currentStudent = null
            }
            student_course_button_delete.isEnabled = false
        }
    }


}