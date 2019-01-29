package com.example.naveen.roomprt.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Questions(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "ques") var ques: String,
    @ColumnInfo(name = "option1") var option1: String,
    @ColumnInfo(name = "option2") var option2: String,
    @ColumnInfo(name = "option3") var option3: String,
    @ColumnInfo(name = "option4") var option4: String,
    @ColumnInfo(name = "answer") var answer: String
)


@Entity
data class QuesCty2(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "ques") var ques: String,
    @ColumnInfo(name = "option1") var option1: String,
    @ColumnInfo(name = "option2") var option2: String,
    @ColumnInfo(name = "option3") var option3: String,
    @ColumnInfo(name = "option4") var option4: String,
    @ColumnInfo(name = "answer") var answer: String
)