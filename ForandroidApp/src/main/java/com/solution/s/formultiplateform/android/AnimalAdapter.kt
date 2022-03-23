package com.solution.s.formultiplateform.android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.solution.s.formultiplateform.android.POJO.Pog_Moadel
import kotlinx.android.synthetic.main.activity_profile.*

 class AnimalAdapter(val list : ArrayList<ModelClass>) : RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    val context: Context? = null
    var db: FirebaseFirestore? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        db = FirebaseFirestore.getInstance()
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.animal_adapter, parent, false)
        return ViewHolder(v)
    }


     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.tv_set_name?.setText(list.get(position).pug)
         holder.tv_set_breed?.setText(list.get(position).breed)
     }


   public class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
       var tv_set_name: TextView? = null
       var tv_set_breed: TextView? = null
       var iv_set_pug: ImageView? = null

        public final class ViewHolder(v: View) {
        }
    }


    override fun getItemCount(): Int {
       return list.size
    }

 }