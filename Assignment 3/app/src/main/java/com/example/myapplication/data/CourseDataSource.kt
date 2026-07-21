package com.example.myapplication.data

import com.example.myapplication.R
import com.example.myapplication.model.Course

object CourseDataSource {
    fun getCourses(): List<Course> {
        return listOf(
            // Assignment card (must appear at top)
            Course(
                titleResId = R.string.course_assignment_title,
                department = "Assignment",
                number = 3,
                capacity = 1
            ),
            // CS courses
            Course(
                titleResId = R.string.course_cs161_title,
                department = "CS",
                number = 161,
                capacity = 100
            ),
            Course(
                titleResId = R.string.course_cs162_title,
                department = "CS",
                number = 162,
                capacity = 100
            ),
            Course(
                titleResId = R.string.course_cs225_title,
                department = "CS",
                number = 225,
                capacity = 200
            ),
            Course(
                titleResId = R.string.course_cs261_title,
                department = "CS",
                number = 261,
                capacity = 200
            ),
            Course(
                titleResId = R.string.course_cs271_title,
                department = "CS",
                number = 271,
                capacity = 200
            ),
            Course(
                titleResId = R.string.course_cs290_title,
                department = "CS",
                number = 290,
                capacity = 200
            ),
            Course(
                titleResId = R.string.course_cs325_title,
                department = "CS",
                number = 325,
                capacity = 300
            ),
            Course(
                titleResId = R.string.course_cs340_title,
                department = "CS",
                number = 340,
                capacity = 300
            ),
            Course(
                titleResId = R.string.course_cs344_title,
                department = "CS",
                number = 344,
                capacity = 300
            ),
            Course(
                titleResId = R.string.course_cs361_title,
                department = "CS",
                number = 361,
                capacity = 300
            ),
            Course(
                titleResId = R.string.course_cs362_title,
                department = "CS",
                number = 362,
                capacity = 300
            ),
            Course(
                titleResId = R.string.course_cs467_title,
                department = "CS",
                number = 467,
                capacity = 400
            )
        )
    }
}
