package com.example.naveen.roomprt

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.example.naveen.roomprt.helper.RoomPreferenceKey
import com.example.naveen.roomprt.helper.RoomPreferencesHelper
import com.example.naveen.roomprt.questions.Questions1
import com.example.naveen.roomprt.room.QuesCategory
import com.example.naveen.roomprt.room.QuesDbInitializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_home.*
import java.io.IOException
import kotlin.collections.ArrayList
import android.widget.RadioButton
import com.example.naveen.roomprt.questions.AnsCheckList
import android.support.v7.app.AlertDialog



class HomeActivity : AppCompatActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private lateinit var quesDbInitializer: QuesDbInitializer
    private val category = listOf("News", "Videos", "Images", "Audio", "Profile")

    private var listQuestions = ArrayList<Questions1>()
    private var sublist: List<Questions1> = ArrayList()
    private var ansCheckList = ArrayList<AnsCheckList>()


    private var currentQuesPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val databaseState = RoomPreferencesHelper.getStringValue(this, RoomPreferenceKey.databaseState)
        quesDbInitializer = QuesDbInitializer(application)

        if (databaseState.equals(RoomPreferenceKey.databaseStateYes)) {
            Toast.makeText(applicationContext, "All Ready Created", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "New Database will be created", Toast.LENGTH_LONG).show()
            addCategory(quesDbInitializer)
        }

        btnCategory.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnPrevious.setOnClickListener(this)
        radioGp.setOnCheckedChangeListener(this)
        btnCheckResult.setOnClickListener(this)

        quesDbInitializer.getAllCategory().observe(this, Observer {
        })

    }


    fun addCategory(quesDbInitializer: QuesDbInitializer) {
        for (items in category) {
            val quesCategory = QuesCategory(items)
            quesDbInitializer.insertAll(quesCategory)
        }
    }


    fun getAllCategory() {
        Toast.makeText(
            applicationContext,
            "SIZE == " + quesDbInitializer.getAllCategory().value?.size,
            Toast.LENGTH_LONG
        ).show()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCategory -> { jsonConverter() }

            R.id.btnPrevious -> { previousQuiz() }

            R.id.btnNext -> { nextQuiz() }

            R.id.btnCheckResult->{ showResult()}
        }
    }

    fun jsonConverter() {
        val gson = Gson()
        listQuestions = gson.fromJson(loadJSONFromAsset(), object : TypeToken<List<Questions1>>() {}.type)
        sublist = listQuestions.subList(0, 10)
        loadQuestion(currentQuesPosition)
        createAnswer(0)
    }

    fun nextQuiz() {
        currentQuesPosition++
        createAnswer(0)
        if (currentQuesPosition == sublist.size - 1) {
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.INVISIBLE
        } else {
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.INVISIBLE
        }
        loadQuestion(currentQuesPosition)
        setAnswerIsthere()
    }

    fun previousQuiz() {
        currentQuesPosition--
        if (currentQuesPosition == 0) {
            btnPrevious.visibility = View.INVISIBLE
            btnNext.visibility = View.VISIBLE
        } else {
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }
        btnCheckResult.visibility = View.GONE
        loadQuestion(currentQuesPosition)
        setAnswerIsthere()
    }


    fun loadQuestion(currentQuesPosition: Int) {

        txtQues.text = sublist[currentQuesPosition].Q
        rdbtn1.text = sublist[currentQuesPosition].O1
        rdbtn2.text = sublist[currentQuesPosition].O2
        rdbtn3.text = sublist[currentQuesPosition].O3
        rdbtn4.text = sublist[currentQuesPosition].O4

        txtCount.text = (currentQuesPosition + 1).toString() + " \t/\t " + sublist.size.toString()

    }

    fun setAnswerIsthere() {
        when (ansCheckList[currentQuesPosition].answer) {
            1 -> {
                rdbtn1.isChecked = true
            }
            2 -> {
                rdbtn2.isChecked = true
            }
            3 -> {
                rdbtn3.isChecked = true
            }
            4 -> {
                rdbtn4.isChecked = true
            }
            0 -> {
                radioGp.clearCheck()
            }
        }
    }

    fun loadJSONFromAsset(): String {
        var json: String? = ""
        try {
            val inputStream = assets.open("convertcsv.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }

        return json
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        val checkedRadioButton = group?.findViewById(checkedId) as RadioButton?
        val isChecked: Boolean = checkedRadioButton?.isChecked ?: false
        val idx = group?.indexOfChild(checkedRadioButton)
        if (isChecked) {
            if(currentQuesPosition == sublist.size - 1){
                btnPrevious.visibility = View.VISIBLE
                btnNext.visibility = View.INVISIBLE
                btnCheckResult.visibility = View.VISIBLE
            }else if(currentQuesPosition == 0){
                btnPrevious.visibility = View.INVISIBLE
                btnNext.visibility = View.VISIBLE
                btnCheckResult.visibility = View.GONE
            }else{
                btnCheckResult.visibility = View.GONE
                btnNext.visibility = View.VISIBLE
                btnPrevious.visibility = View.VISIBLE
            }
            updateAnswer((idx?.plus(1) ?: 0))
        } else {
            btnCheckResult.visibility = View.GONE
            if(currentQuesPosition == sublist.size - 1){
                btnPrevious.visibility = View.VISIBLE
                btnNext.visibility = View.INVISIBLE
            }else if(currentQuesPosition == 0){
                btnPrevious.visibility = View.INVISIBLE
                btnNext.visibility = View.VISIBLE
            }else{
                btnPrevious.visibility = View.VISIBLE
                btnNext.visibility = View.INVISIBLE
            }
        }
    }

    fun createAnswer(answer: Int) {
        if (currentQuesPosition >= ansCheckList.size) {
            ansCheckList.add(
                currentQuesPosition, AnsCheckList(currentQuesPosition, sublist[currentQuesPosition].A, answer
                )
            )
        }
    }


    fun updateAnswer(answer: Int) {
        ansCheckList[currentQuesPosition] = AnsCheckList(currentQuesPosition, sublist[currentQuesPosition].A, answer
        )
    }


    fun showResult(){
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra(RoomPreferenceKey.resultArray,ansCheckList)
        startActivity(intent)
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Terminate test")
            .setMessage("Are you sure you want to close this test?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()

    }


}
