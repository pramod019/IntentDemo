package com.pramodk.myapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
	data class Student(
    val name: String = "Anupam",
    val age: Int = 24
) : Parcelable
