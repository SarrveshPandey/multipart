package com.solution.s.formultiplateform.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

@Suppress("DEPRECATION")
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        val s1 = sh.getString("email", "")
        val a = sh.getString("password", "")

        if (s1.isNullOrEmpty()) {
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
        else if(a.isNullOrEmpty()){
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
            finish()
        }
        else{
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

            Handler().postDelayed({
                val intent = Intent(this, ListAnimal::class.java)
                startActivity(intent)
                finish()
            }, 3000) // 3000 is the delayed time in milliseconds.
        }


    }
}