package com.example.naveen.roomprt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.naveen.roomprt.helper.RoomPreferenceKey
import com.example.naveen.roomprt.questions.AnsCheckList
import kotlinx.android.synthetic.main.result_activity.*

class ResultActivity : AppCompatActivity() {

    private var bundle: Bundle? = null
    private lateinit var ansCheckList: ArrayList<AnsCheckList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)
        init()
    }

    fun init() {
        bundle = intent.extras
        if (bundle != null) {
            if (bundle?.containsKey(RoomPreferenceKey.resultArray) == true) {
                ansCheckList =
                        bundle?.getParcelableArrayList<AnsCheckList>(RoomPreferenceKey.resultArray) as ArrayList<AnsCheckList>
                showResult()
            }
        }

    }

    fun showResult() {
        var result = 0
        for (item in ansCheckList) {
            if (item.answer == item.selected) {
                result++
            } else {

            }
        }
        txtResult.text = result.toString() + "\t/\t" + ansCheckList.size.toString()
    }
}