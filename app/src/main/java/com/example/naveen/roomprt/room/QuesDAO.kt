package com.example.naveen.roomprt.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface QuesDAO {

    @Query("SELECT * FROM quescategorytable")
    fun getAllData() : List<QuesCategory>

    @Insert
    fun insertAll(quesCategory: QuesCategory)

    @Query("SELECT * FROM quescategorytable")
    fun getAllDataLive() : LiveData<List<QuesCategory>>

}