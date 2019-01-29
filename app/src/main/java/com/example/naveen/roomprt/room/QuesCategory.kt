package com.example.naveen.roomprt.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "quescategorytable")
data class QuesCategory(@ColumnInfo(name = "categoryName") var categoryName: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

