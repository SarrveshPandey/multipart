package com.solution.s.formultiplateform.android


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.solution.s.formultiplateform.android.POJO.Pog_Moadel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_adapter.*
import kotlinx.android.synthetic.main.activity_profile.*

class ListAnimal : AppCompatActivity() {

    private lateinit var animalAdapter: AnimalAdapter

    lateinit var classss: ModelClass

    var db: FirebaseFirestore? = null

    var list: ArrayList<ModelClass> = ArrayList()

    var list1: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_adapter)

        db = FirebaseFirestore.getInstance()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rv_animal.setLayoutManager(layoutManager)

        tv_add_pug.setOnClickListener {
            intent = Intent(this, Pug::class.java)
            startActivity(intent)
        }

        tv_add_cat.setOnClickListener {
            intent = Intent(this, Add_Cat::class.java)
            startActivity(intent)
        }

        tv_profile.setOnClickListener {
            intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        db!!.collection("Pugs")
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc != null) {
                        for (i in 0 until list.size) {
                            list1.add(list[i].breed.toString())
                        }
                    }

                    if (doc != null) {
                    }
                    animalAdapter = AnimalAdapter(list)
                    rv_animal.adapter = animalAdapter

                    Toast.makeText(this, "" + list, Toast.LENGTH_LONG).show()
                }

            }
    }

}


