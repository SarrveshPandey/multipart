package com.solution.s.formultiplateform.android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_second2.*


class SecondActivity : AppCompatActivity() {

    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    val passwordMatcher = Regex(passwordPattern)

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second2)

        auth = Firebase.auth

        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val myEdit = sharedPreferences.edit()


        btn_submit.setOnClickListener(View.OnClickListener {

                if ( et_email.text.trim().isEmpty()){
                    et_email.setError("please enter email")
                    val strArray: String = et_email.getText().toString().toString().replace("\\s+", "")
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.text.toString()).matches()){
                    et_email.setError("please enter valid email")
                }
                else  if ( et_password.text.trim().isEmpty()){
                    et_password.setError("please enter password")
                }
                else  if ( et_password.text.matches(passwordMatcher) ){
                    et_password.setError("please use valid password")
                }
                else{

                    val   email:String = et_email.text.toString()
                    val    pass:String = et_password.text.toString()

                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {

                                myEdit.putString("email", email)
                                myEdit.putString("password", pass)
                                myEdit.apply()

                                Toast.makeText(this, "Successfully Register", Toast.LENGTH_SHORT).show()
                                intent = Intent(this,Newform::class.java)
                                startActivity(intent)
                            }
                            else {
                                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                            }
                        } }
        })

    }


}

