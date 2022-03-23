package com.solution.s.formultiplateform.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.solution.s.formultiplateform.Greeting
import android.widget.TextView

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        tv.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        })
    }
}
