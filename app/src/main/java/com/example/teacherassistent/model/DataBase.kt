package com.example.teacherassistent.model

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class, Course::class, StudentCourse::class, Grade::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao
    abstract fun studentCourseDao(): StudentCourseDao
    abstract fun gradeDao(): GradeDao

    companion object{
        @Volatile
        private var INSTANCE: DataBase ?= null

        fun getDatabase(context: Context): DataBase {
            val tempInstance= INSTANCE

            if(tempInstance!=null)
                return tempInstance
            else
                synchronized(this)
                {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "my_database"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}