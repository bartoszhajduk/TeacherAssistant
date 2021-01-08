package com.example.teacherassistent.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R

class GradeListTodayAdapter(var gradeViewModel: GradeViewModel) : RecyclerView.Adapter<GradeListTodayAdapter.StudentHolder>() {

    private lateinit var fragView: ViewParent

    inner class StudentHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view= LayoutInflater.from(parent.context).inflate(
                R.layout.grade_list_today_row,
                parent,
                false
        )
        fragView = parent.parent
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val textViewName= holder.itemView.findViewById<TextView>(R.id.grade_list_today_row_name)
        val textViewSurname= holder.itemView.findViewById<TextView>(R.id.grade_list_today_row_surname)
        val textViewSubject= holder.itemView.findViewById<TextView>(R.id.grade_list_today_row_subject)
        val textViewGrade= holder.itemView.findViewById<TextView>(R.id.label_grade_grade)
        val textViewComment= holder.itemView.findViewById<TextView>(R.id.label_grade_comment)
        val textViewDate= holder.itemView.findViewById<TextView>(R.id.label_grade_date)

        textViewName.text = gradeViewModel.todayGrades.value?.get(position)?.name.toString()
        textViewSurname.text = gradeViewModel.todayGrades.value?.get(position)?.surname.toString()
        textViewSubject.text = gradeViewModel.todayGrades.value?.get(position)?.nameOfCourse.toString()
        textViewGrade.text = gradeViewModel.todayGrades.value?.get(position)?.grade.toString()
        textViewComment.text = gradeViewModel.todayGrades.value?.get(position)?.comment.toString()
        textViewDate.text = gradeViewModel.todayGrades.value?.get(position)?.date.toString()
    }

    override fun getItemCount(): Int {
        return gradeViewModel.todayGrades.value?.size ?: 0
    }
}