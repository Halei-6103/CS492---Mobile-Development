package com.example.myapplication.model

data class Course(
    val titleResId: Int,  // String Resource Id, which resolves to Int
    val department: String,
    val number: Int,
    val capacity: Int
)
