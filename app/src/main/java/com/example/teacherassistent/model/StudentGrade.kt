package com.example.teacherassistent.model

import javax.security.auth.Subject

data class StudentGrade(val name: String,
                        val surname: String,
                        val nameOfCourse: String,
                        var grade: Double,
                        var comment: String,
                        var date: String)