package com.solution.s.formultiplateform.android

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        val s1 = sh.getString("email", "")
        val a = sh.getString("password", "")

        name.setText(s1)
        pass.setText(a)


        img_burger.setOnClickListener(View.OnClickListener {

            val popupMenu: PopupMenu = PopupMenu(this,img_burger)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.clear ->{
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        sh.edit().clear().apply()

                        intent = Intent(this,SecondActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                true
            })
            popupMenu.show()
    })

    }
}