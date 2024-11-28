package com.lyj.timecapsule

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        var title = findViewById<EditText>(R.id.editTitleText_write)
        var content = findViewById<EditText>(R.id.editContentText_write)
        var calButton = findViewById<Button>(R.id.buttonCal)
        var time = findViewById<TimePicker>(R.id.time_picker)
        var share = findViewById<EditText>(R.id.editShareText_write)


    }
}