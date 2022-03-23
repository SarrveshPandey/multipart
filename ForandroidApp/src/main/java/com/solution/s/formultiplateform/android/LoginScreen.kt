package com.solution.s.formultiplateform.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_screen.*
import java.util.*


class LoginScreen : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        auth = FirebaseAuth.getInstance()

        val myEdit = getSharedPreferences("MySharedPref", MODE_PRIVATE).edit()


        btn_login.setOnClickListener(View.OnClickListener {
            intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        })

        btn_submit1.setOnClickListener(View.OnClickListener {
            
                val email = et_email1.text.toString()
                val pass = et_passwor2.text.toString()
                // calling signInWithEmailAndPassword(email, pass)
                // function using Firebase auth object
                // On successful response Display a Toast
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()

                        myEdit.putString("email", email)
                        myEdit.putString("password", pass)
                        myEdit.apply()

                        intent = Intent(this,ListAnimal::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                    }
                }
        })
    }



}