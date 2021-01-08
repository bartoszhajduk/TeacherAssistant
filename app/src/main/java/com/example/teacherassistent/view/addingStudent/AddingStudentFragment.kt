package com.example.teacherassistent.view.addingStudent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R
import com.example.teacherassistent.model.Student
import com.example.teacherassistent.viewmodel.StudentListAdapter
import com.example.teacherassistent.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_adding_student.*

class AddingStudentFragment : Fragment() {

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var studentListAdapter: StudentListAdapter
    private lateinit var studentLayoutManager: LinearLayoutManager
    private lateinit var studentRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        studentLayoutManager = LinearLayoutManager(context)
        studentViewModel = ViewModelProvider(requireActivity()).get(StudentViewModel::class.java)
        studentListAdapter = StudentListAdapter(studentViewModel)

        studentViewModel.students.observe(viewLifecycleOwner, {
            studentListAdapter.notifyDataSetChanged()
        })

        val root = inflater.inflate(R.layout.fragment_adding_student, main_container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      studentRecyclerView = student_recycler.apply {
        this.layoutManager = studentLayoutManager
        this.adapter = studentListAdapter
      }

        student_input_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                studentViewModel.filteredStudents(position + 1)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

      student_button_add.setOnClickListener {
        studentViewModel.addStudent(
          student_input_name.text.toString(),
          student_input_surname.text.toString(),
          student_input_spinner.selectedItem.toString().toInt())

        student_button_edit.isEnabled = false
      }

      student_button_edit.setOnClickListener {
        studentViewModel.update(
          Student(studentViewModel.currentStudent?.id!!,
          student_input_name.text.toString(),
          student_input_surname.text.toString(),
          student_input_spinner.selectedItem.toString().toInt()))
        student_button_edit.isEnabled = false
      }
    }
}
