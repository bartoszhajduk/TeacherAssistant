package com.example.teacherassistent.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R

import kotlinx.android.synthetic.main.student_list_course_row.view.*


class StudentCourseListAdapterSecond(var groupViewModel: GroupViewModel, var courseViewModel: CourseViewModel) : RecyclerView.Adapter<StudentCourseListAdapterSecond.StudentHolder>() {

    private lateinit var fragView: ViewParent
    private lateinit var deleteButton: Button

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

        textViewFirstName.text = groupViewModel.group.value?.get(position)?.name.toString()
        textViewLastName.text = groupViewModel.group.value?.get(position)?.surname.toString()

        deleteButton = (fragView as View).findViewById<View>(R.id.student_course_button_delete) as Button

        holder.itemView.student_course_list_row.setOnClickListener {
            groupViewModel.currentStudent = groupViewModel.group.value?.get(position)!!
            //println(groupViewModel.currentStudent?.id)
            groupViewModel.getCurrentStudentCourse(courseViewModel.currentCourse?.id, groupViewModel.currentStudent?.id)
            //println(""+courseViewModel.currentCourse?.id + groupViewModel.currentStudent?.id)
            //println(groupViewModel.currentGroup.value)
            deleteButton.isEnabled = true
            /*
            studentViewModel.currentStudent = groupViewModel.group.value?.get(position)!!
            groupViewModel.getCurrentStudentCourse(courseViewModel.currentCourse?.id, studentViewModel.currentStudent?.id)
            println("" + courseViewModel.currentCourse?.id + studentViewModel.currentStudent?.id)
            println(groupViewModel.currentGroup)
            */
            //studentViewModel.currentStudent = studentViewModel.students.value?.get(position)!!
        }

    }

    override fun getItemCount(): Int {
        return groupViewModel.group.value?.size ?: 0
    }

}