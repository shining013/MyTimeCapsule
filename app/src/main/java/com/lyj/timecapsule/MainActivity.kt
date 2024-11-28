package com.lyj.timecapsule

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageButton>(R.id.btnCreateTC).setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent);
        }

        findViewById<ImageButton>(R.id.btnViewTC).setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }
    }
}