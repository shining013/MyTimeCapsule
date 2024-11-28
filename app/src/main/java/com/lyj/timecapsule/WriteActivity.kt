package com.lyj.timecapsule

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
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

    // 타임 캡슐 시간 체크
    fun chkPastDate(date: String, time: String): Boolean{
        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val chkCalendar = Calendar.getInstance()
        chkCalendar.timeZone = TimeZone.getTimeZone("Asia/Seoul")

        val d = capsule.date.split("/")
        val t = capsule.time.split("/")
        chkCalendar.set(Calendar.YEAR, d[0].toInt())
        chkCalendar.set(Calendar.MONTH, d[1].toInt() - 1)
        chkCalendar.set(Calendar.DAY_OF_MONTH, d[2].toInt())
        chkCalendar.set(Calendar.HOUR_OF_DAY, t[0].toInt())
        chkCalendar.set(Calendar.MINUTE, t[1].toInt())

        println("current: ${currentCalendar.get(Calendar.YEAR)}," +
                "${currentCalendar.get(Calendar.MONTH)}," +
                "${currentCalendar.get(Calendar.DAY_OF_MONTH)}," +
                "${currentCalendar.get(Calendar.HOUR_OF_DAY)}," +
                "${currentCalendar.get(Calendar.MINUTE)}")
        println("chkCalendar: ${chkCalendar.get(Calendar.YEAR)}," +
                "${chkCalendar.get(Calendar.MONTH)}," +
                "${chkCalendar.get(Calendar.DAY_OF_MONTH)}," +
                "${chkCalendar.get(Calendar.HOUR_OF_DAY)}," +
                "${chkCalendar.get(Calendar.MINUTE)}")
        // 미래 시간인 경우 통과
        if(chkCalendar.after(currentCalendar)) return false
        else return true
    }

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
        calendar.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        time.setHour(hour)
        time.setMinute(minute)
        capsule.date = "$year/${month + 1 }/${day}"
        capsule.time = "$hour/$minute"

        // 날짜 선택
        calButton.setOnClickListener{
            val datePickerDialog = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay ->
                capsule.date = "$selectedYear/${selectedMonth + 1 }/$selectedDay"
                calButton.text = capsule.date
            }, year, month, day)
            datePickerDialog.show()
        }

        // 시간 선택
        time.setOnTimeChangedListener { _, selectedHour, selectedMinute ->
            capsule.time = "$selectedHour/$selectedMinute"
        }

        // 캡슐 생성
        createButton.setOnClickListener {
            val resultIntent = Intent()
            val alertDialog = AlertDialog.Builder(this)
            println(chkPastDate(capsule.date, capsule.time))

            // 제목 본문 null인 경우 경고
            if(title.text?.isEmpty() == true || content.text?.isEmpty() == true){
                alertDialog.setTitle("캡슐 생성 실패")
                alertDialog.setMessage("제목과 본문은 필수로 작성해야 합니다")
                alertDialog.setPositiveButton("확인"){ dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
            // 과거 시간인 경우 경고
            else if(chkPastDate(capsule.date, capsule.time)){
                alertDialog.setTitle("캡슐 생성 실패")
                alertDialog.setMessage("타임캡슐을 과거에 묻을 수 없습니다.")
                alertDialog.setPositiveButton("확인"){ dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
            else{
                capsule.title = title.text.toString()
                capsule.content = content.text.toString()
                capsule.friend = friend.text.toString()

                if(capsule.friend?.isEmpty() == true) capsule.friend = "none"

                println("${capsule.title}, ${capsule.content}, ${capsule.date}, ${capsule.time}, ${capsule.friend}")

                // 저장확인
                alertDialog.setTitle("확인")
                alertDialog.setMessage("캡슐을 저장하시겠습니까?")
                alertDialog.setPositiveButton("확인"){ dialog, _ ->
                    dialog.dismiss()
                    var size = SharedData.capsuleList.size
                    // 리스트에 저장
                    SharedData.capsuleList.add(capsule)
                    var size2 = SharedData.capsuleList.size
                    if((size2-size)==1){
                        println("캡슐 생성 성공")
                    }
//                    // 확인 시 저장
//                    val resultIntent = Intent()
//                    resultIntent.putExtra("title", capsule.title)
//                    resultIntent.putExtra("content", capsule.content)
//                    resultIntent.putExtra("date", capsule.date)
//                    resultIntent.putExtra("time", capsule.time)
//                    resultIntent.putExtra("friend", capsule.friend)
//                    setResult(Activity.RESULT_OK, resultIntent)
//                    println("캡슐 생성 성공")
                    finish()
                }
                alertDialog.setNegativeButton("취소"){ dialog, _ ->
                    dialog.dismiss()
                    println("캡슐 생성 취소")
                    return@setNegativeButton
                }
                alertDialog.show()

            }
        }

        // 생성 취소
        cancleButton.setOnClickListener {
            onBackPressed()
        }
    }
}