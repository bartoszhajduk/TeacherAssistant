package com.example.teacherassistent.view.addingCourse

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
import com.example.teacherassistent.viewmodel.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_adding_course.*
import kotlinx.android.synthetic.main.fragment_adding_student.*

class AddingCourseFragment : Fragment() {

  private lateinit var courseViewModel: CourseViewModel
  private lateinit var courseListAdapter: CourseListAdapter
  private lateinit var courseLayoutManager: LinearLayoutManager
  private lateinit var courseRecyclerView: RecyclerView

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    courseLayoutManager = LinearLayoutManager(context)
    courseViewModel = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
    courseListAdapter = CourseListAdapter(courseViewModel)

    courseViewModel.courses.observe(viewLifecycleOwner, {
      courseListAdapter.notifyDataSetChanged()
    })

    val root = inflater.inflate(R.layout.fragment_adding_course, main_container, false)
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    courseRecyclerView = course_recycler.apply {
      this.layoutManager = courseLayoutManager
      this.adapter = courseListAdapter
    }

    course_input_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        courseViewModel.filteredCourses(position + 1)
      }
      override fun onNothingSelected(parent: AdapterView<*>?) {
      }
    }

    course_button_add.setOnClickListener {
      courseViewModel.addCourse(
              course_input_name.text.toString(),
              course_input_spinner.selectedItem.toString().toInt())
      course_button_edit.isEnabled = false
    }

    course_button_edit.setOnClickListener {
      courseViewModel.update(
              Course(courseViewModel.currentCourse?.id!!,
                      course_input_name.text.toString(),
                      course_input_spinner.selectedItem.toString().toInt()))
      course_button_edit.isEnabled = false
    }
  }


}