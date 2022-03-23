package com.solution.s.formultiplateform.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_adapter.*
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

     var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        db = FirebaseFirestore.getInstance()

        tv_editprofile.setOnClickListener {
            intent = Intent(this,Newform::class.java)
            startActivity(intent)
        }

    }


    override fun onStart() {
        super.onStart()
        progresss.visibility = View.VISIBLE
        constraintLayout3.visibility = View.GONE
        db!!.collection("User")
            .document("data")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val doc = task.result
                    tv_name.text = doc!!.getString("name")
                    tv_mail.text = doc!!.getString("email")
                    tv_phone.text = doc!!.getString("phone")

                    //set email next to bio TextView
                    tv_bio.text = doc.getString("bio")
                    val img_url = doc["image"] as String?
                    Picasso.get().load(img_url).into(iv_profileimg)

                    progresss.visibility = View.GONE
                    constraintLayout3.visibility = View.VISIBLE
                }
            }
    }


}