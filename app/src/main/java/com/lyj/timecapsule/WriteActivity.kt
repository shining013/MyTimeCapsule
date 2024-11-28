package com.lyj.timecapsule

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lyj.timecapsule.model.Capsule

class WriteActivity : AppCompatActivity() {
    val capsule: Capsule = Capsule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        var title = findViewById<EditText>(R.id.editTitleText_write)
        var content = findViewById<EditText>(R.id.editContentText_write)
        var calButton = findViewById<Button>(R.id.buttonCal)
        var time = findViewById<TimePicker>(R.id.time_picker)
        var friend = findViewById<EditText>(R.id.editShareText_write)
        var createButton = findViewById<Button>(R.id.buttonCreate)
        var cancleButton = findViewById<Button>(R.id.buttonCancle)

        // 날짜, 시간 선택하기 전 기본 값
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        capsule.date = "$year/${month + 1 }/${day + 1}"

        // 날짜 선택
        calButton.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay ->
                capsule.date = "$selectedYear/${selectedMonth + 1 }/$selectedDay"
                calButton.text = capsule.date
            }, year, month, day)

            datePickerDialog.show()
        }

        // TimePicker 클릭 이벤트
        time.setOnTimeChangedListener { _, hour, minute ->
            capsule.time = "$hour/$minute"
        }


        createButton.setOnClickListener {
            val resultIntent = Intent()
            val alertDialog = AlertDialog.Builder(this)

            // 제목 본문 null인 경우 경고
            if(title.text?.isEmpty() == true || content.text?.isEmpty() == true){
                alertDialog.setTitle("캡슐 저장 실패")
                alertDialog.setMessage("제목과 본문은 필수로 작성해야 합니다")
                alertDialog.setPositiveButton("확인"){ dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
            else{
                capsule.title = title.text.toString()
                capsule.content = content.text.toString()
                capsule.friend = friend.text.toString()

                println("${capsule.title}, ${capsule.content}, ${capsule.date}, ${capsule.time}, ${capsule.friend}")
            }
        }

        cancleButton.setOnClickListener {
            onBackPressed()
        }
    }
}