package com.example.teacherassistent.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R
import kotlinx.android.synthetic.main.student_list_row.view.*


class StudentListAdapter(var studentViewModel: StudentViewModel) : RecyclerView.Adapter<StudentListAdapter.StudentHolder>() {

    private lateinit var nameToSend: TextView
    private lateinit var surnameToSend: TextView
    private lateinit var semesterToSend: Spinner
    private lateinit var fragView: ViewParent
    private lateinit var editButton: Button

    inner class StudentHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.student_list_row,
            parent,
            false
        )
        fragView = parent.parent
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val textViewID= holder.itemView.findViewById<TextView>(R.id.student_list_row_id)
        val textViewFirstName= holder.itemView.findViewById<TextView>(R.id.student_list_row_name)
        val textViewLastName= holder.itemView.findViewById<TextView>(R.id.student_list_row_surname)
        val textViewSemester= holder.itemView.findViewById<TextView>(R.id.student_list_row_semester)

        textViewID.text = studentViewModel.students.value?.get(position)?.id.toString()
        textViewFirstName.text = studentViewModel.students.value?.get(position)?.name.toString()
        textViewLastName.text = studentViewModel.students.value?.get(position)?.surname.toString()
        textViewSemester.text = studentViewModel.students.value?.get(position)?.semester.toString()

        nameToSend = (fragView as View).findViewById<View>(R.id.student_input_name) as TextView
        surnameToSend = (fragView as View).findViewById<View>(R.id.student_input_surname) as TextView
        semesterToSend = (fragView as View).findViewById<View>(R.id.student_input_spinner) as Spinner
        editButton = (fragView as View).findViewById<View>(R.id.student_button_edit) as Button

        holder.itemView.findViewById<Button>(R.id.student_button_delete).setOnClickListener {
            studentViewModel.deleteStudent(studentViewModel.students.value?.get(position))
            editButton.isEnabled = false
        }

        holder.itemView.student_list_row.setOnClickListener {
            studentViewModel.currentStudent = studentViewModel.students.value?.get(position)!!
            nameToSend.text = textViewFirstName.text
            surnameToSend.text = textViewLastName.text
            semesterToSend.setSelection(textViewSemester.text.toString().toInt() - 1)
            editButton.isEnabled = true
        }

    }

    override fun getItemCount(): Int {
        return studentViewModel.students.value?.size ?: 0
    }

}