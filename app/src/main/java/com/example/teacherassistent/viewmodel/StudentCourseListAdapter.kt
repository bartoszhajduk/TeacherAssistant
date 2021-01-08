package com.example.teacherassistent.viewmodel

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R

import kotlinx.android.synthetic.main.student_list_course_row.view.*


class StudentCourseListAdapter(var studentViewModel: StudentViewModel) : RecyclerView.Adapter<StudentCourseListAdapter.StudentHolder>() {

    private lateinit var fragView: ViewParent

    inner class StudentHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.student_list_course_row,
            parent,
            false
        )
        fragView = parent.parent.parent
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {

        val textViewFirstName= holder.itemView.findViewById<TextView>(R.id.student_course_list_row_name)
        val textViewLastName= holder.itemView.findViewById<TextView>(R.id.student_course_list_row_surname)

        //semester = (fragView as View).findViewById<View>(R.id.student_course_input_spinner) as Spinner


        textViewFirstName.text = studentViewModel.students.value?.get(position)?.name.toString()
        textViewLastName.text = studentViewModel.students.value?.get(position)?.surname.toString()


        holder.itemView.student_course_list_row.setOnClickListener {
            studentViewModel.currentStudent = studentViewModel.students.value?.get(position)!!
        }



    }

    override fun getItemCount(): Int {
        return studentViewModel.students.value?.size ?: 0
    }

}