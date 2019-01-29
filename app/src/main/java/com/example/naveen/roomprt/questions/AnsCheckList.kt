package com.example.naveen.roomprt.questions

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  AnsCheckList(val position : Int,
                         val selected : Int,
                         val answer : Int) : Parcelable
