package com.lyj.timecapsule

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
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

    override fun onResume() {
        super.onResume()

        val listView: ListView = findViewById(R.id.listView)

        val list = SharedData.capsuleList.map {it.title}
        // ArrayAdapter 설정
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        // ListView에 Adapter 연결
        listView.adapter = adapter

        listView.setOnItemClickListener(){parent, view, position, id  ->
            val selectedItem = list[position]

            val intent = Intent(this, ReadActivity::class.java).apply {
                putExtra("title",selectedItem)
            }
            startActivity(intent)
        }
    }
}