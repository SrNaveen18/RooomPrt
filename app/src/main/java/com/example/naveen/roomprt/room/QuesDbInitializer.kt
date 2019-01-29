package com.example.naveen.roomprt.room

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class QuesDbInitializer(application: Application) {

    private val quesDAO: QuesDAO
    private val liveAllData : LiveData<List<QuesCategory>>

    init {
        val quesdb: QuesDataBase = QuesDataBase.getQuesDataBase(application.applicationContext)!!
        quesDAO = quesdb.quesDao()
        liveAllData = quesdb.quesDao().getAllDataLive()
    }

    fun insertAll(quesCategory: QuesCategory) {
        InsertAllDataAsynchTask(quesDAO).execute(quesCategory)
    }

    fun getAllCategory(): LiveData<List<QuesCategory>> {
        return liveAllData
    }


    companion object {

        class InsertAllDataAsynchTask(val userDAO: QuesDAO) : AsyncTask<QuesCategory, Void, Void>() {
            override fun doInBackground(vararg params: QuesCategory): Void? {
                userDAO.insertAll(params[0])
                return null
            }
        }

//        class PopulateAllDataAsynchTask(val userDAO: QuesDAO) : AsyncTask<Void, Void, Void>() {
//            override fun doInBackground(vararg params: Void): Void? {
//                showAllValues(userDAO)
//                return null
//            }
//        }
//
//        fun showAllValues(userDAO: QuesDAO) {
//            Log.w("ROOMDATABASE", "CategorySize ===== " + userDAO.getAllData().size)
//        }
    }


//    fun populateAllValue() {
//        PopulateAllDataAsynchTask(quesDAO).execute()
//    }
}