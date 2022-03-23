package com.solution.s.formultiplateform.android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_img_upload.*
import java.io.IOException
import java.util.*

class ImgUpload : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var db: FirebaseFirestore? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_upload)

        db = FirebaseFirestore.getInstance()

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        tv_changephoto2.setOnClickListener { launchGallery() }
        btn_savechange2.setOnClickListener { filePath?.let { it1 -> uploadImage(it1) } }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                iv_editimg2.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }



    private fun uploadImage(filepath: Uri) {

            if (filePath != null){

                var ref = storageReference!!.child("Post/" + UUID.randomUUID().toString())


            ref.putFile(filepath)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->

                        Toast.makeText(baseContext, "Upload success...", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
        }
            }


    /*  private fun addUploadRecordToDb(imageUrl: String){
      val db = FirebaseFirestore.getInstance()

      val data = HashMap<String, Any>()
      data["imageUrl"] = imageUrl

      db.collection("posts")
          .add(data)
          .addOnSuccessListener { documentReference ->
              Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
          }
          .addOnFailureListener { e ->
              Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
          }
  }*/



}

