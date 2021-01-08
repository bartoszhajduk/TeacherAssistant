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
import kotlinx.android.synthetic.main.course_list_row.view.*


class CourseListAdapter(var courseViewModel: CourseViewModel) : RecyclerView.Adapter<CourseListAdapter.CourseHolder>() {

    private lateinit var nameToSend: TextView
    private lateinit var semesterToSend: Spinner
    private lateinit var fragView: ViewParent
    private lateinit var editButton: Button

    inner class CourseHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.course_list_row,
                parent,
                false
        )
        fragView = parent.parent
        return CourseHolder(view)
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        val textViewID= holder.itemView.findViewById<TextView>(R.id.label_course_id)
        val textViewName= holder.itemView.findViewById<TextView>(R.id.label_course_name)
        val textViewSemester= holder.itemView.findViewById<TextView>(R.id.label_course_semester)

        textViewID.text = courseViewModel.courses.value?.get(position)?.id.toString()
        textViewName.text = courseViewModel.courses.value?.get(position)?.nameOfCourse.toString()
        textViewSemester.text = courseViewModel.courses.value?.get(position)?.semester.toString()

        nameToSend = (fragView as View).findViewById<View>(R.id.course_input_name) as TextView
        semesterToSend = (fragView as View).findViewById<View>(R.id.course_input_spinner) as Spinner
        editButton = (fragView as View).findViewById<View>(R.id.course_button_edit) as Button

        holder.itemView.findViewById<Button>(R.id.course_button_delete).setOnClickListener {
            courseViewModel.deleteCourse(courseViewModel.courses.value?.get(position))
            editButton.isEnabled = false
        }

        holder.itemView.course_list_student_row.setOnClickListener {
            courseViewModel.currentCourse = courseViewModel.courses.value?.get(position)!!
            nameToSend.text = textViewName.text
            semesterToSend.setSelection(textViewSemester.text.toString().toInt() - 1)
            editButton.isEnabled = true
        }

    }

    override fun getItemCount(): Int {
        return courseViewModel.courses.value?.size ?: 0
    }

}