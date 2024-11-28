package com.lyj.timecapsule

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        findViewById<Button>(R.id.buttonShow).setOnClickListener {
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent);
        }
    }
}