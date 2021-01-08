package com.example.teacherassistent.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R


class GradeListAdapter(var gradeViewModel: GradeViewModel) : RecyclerView.Adapter<GradeListAdapter.StudentHolder>() {

    private lateinit var fragView: ViewParent

    inner class StudentHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view= LayoutInflater.from(parent.context).inflate(
            R.layout.grade_list_row,
            parent,
            false
        )
        fragView = parent.parent
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val textViewGrade= holder.itemView.findViewById<TextView>(R.id.label_grade_grade)
        val textViewComment= holder.itemView.findViewById<TextView>(R.id.label_grade_comment)
        val textViewDate= holder.itemView.findViewById<TextView>(R.id.label_grade_date)

        textViewGrade.text = gradeViewModel.grades.value?.get(position)?.grade.toString()
        textViewComment.text = gradeViewModel.grades.value?.get(position)?.comment.toString()
        textViewDate.text = gradeViewModel.grades.value?.get(position)?.date.toString()

        holder.itemView.findViewById<Button>(R.id.grade_button_delete).setOnClickListener {
            gradeViewModel.deleteGrade(gradeViewModel.grades.value?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return gradeViewModel.grades.value?.size ?: 0
    }

}