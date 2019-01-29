package com.example.naveen.roomprt.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [QuesCategory::class], version = 1)
abstract class QuesDataBase : RoomDatabase() {

    abstract fun quesDao(): QuesDAO

    companion object {

        var INSTANCE: QuesDataBase? = null

        fun getQuesDataBase(context: Context): QuesDataBase? {
          if (INSTANCE ==  null){
              synchronized(QuesDataBase::class){
                  INSTANCE = Room.databaseBuilder(context.applicationContext, QuesDataBase::class.java,"QUESDATABASE").build()
              }
          }

            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}