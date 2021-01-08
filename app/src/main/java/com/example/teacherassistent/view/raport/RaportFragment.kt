package com.example.teacherassistent.view.raport

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherassistent.R
import com.example.teacherassistent.viewmodel.*
import kotlinx.android.synthetic.main.fragment_grade.*
import kotlinx.android.synthetic.main.fragment_raport.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RaportFragment : Fragment() {

  private lateinit var gradeViewModel: GradeViewModel

  private lateinit var gradesLayoutManager: LinearLayoutManager
  private lateinit var gradeListTodayAdapter: GradeListTodayAdapter
  private lateinit var gradesRecyclerView: RecyclerView

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    gradeViewModel = ViewModelProvider(requireActivity()).get(GradeViewModel::class.java)
    gradesLayoutManager = LinearLayoutManager(context)
    gradeListTodayAdapter = GradeListTodayAdapter(gradeViewModel)

    gradeViewModel.todayGrades.observe(viewLifecycleOwner, {
      gradeListTodayAdapter.notifyDataSetChanged()
    })

    val root = inflater.inflate(R.layout.fragment_raport, container, false)
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    gradesRecyclerView = raport_recycler.apply {
      this.layoutManager = gradesLayoutManager
      this.adapter = gradeListTodayAdapter
    }

    Calendar.getInstance().time

    gradeViewModel.getTodayGrades(SimpleDateFormat("dd/yyyy").format(Date()))
  }

}