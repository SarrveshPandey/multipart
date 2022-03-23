package com.solution.s.formultiplateform.android

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.solution.s.formultiplateform.android.POJO.Pog_Moadel
import kotlinx.android.synthetic.main.activity_add_cat.*
import kotlinx.android.synthetic.main.activity_newform.*
import kotlinx.android.synthetic.main.activity_pug.*
import java.io.ByteArrayOutputStream
import java.util.*

class Pug : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    var mImageCaptureUri: Uri? = null

     var image =""

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    //for get image to gallery and camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pug)

        storage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        db = FirebaseFirestore.getInstance();

        tv_upload.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Firebase")
            builder.setMessage("Chose from")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Camera") { dialogInterface, which ->
                intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }

            //performing negative action
            builder.setNegativeButton("Gallery") { dialogInterface, which ->
                intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                val intent ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(intent, 1234);
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        btn_save.setOnClickListener(View.OnClickListener {

            val PUG = et_name.text.toString()
            var Breed = et_breed.text.toString()

            if (PUG.isEmpty()) {
                et_name.error = "Please enter Pug Name"
            } else if (Breed.isEmpty()) {
                et_breed.error = "Please enter Breed "
            }
            else if (image.isNullOrEmpty()) {
                Toast.makeText(this,"please attach pug picture",Toast.LENGTH_LONG).show()
            }

            else {
                uploadImage2(PUG, Breed)
            }
        })
    }

    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            mImageCaptureUri = getImage(this, photo)

            image = mImageCaptureUri.toString()
            iv_pug.setImageBitmap(photo)
        } else if (requestCode == 1234 && resultCode == RESULT_OK) {

            if (data != null) {
                mImageCaptureUri = data.data

                image = mImageCaptureUri.toString()
            }
            /*String Path=mImageCaptureUri.getPath();*/
            iv_pug.setImageURI(mImageCaptureUri)
        }
    }

    fun getImage(inContext: Context, photo: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        photo!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), photo, "Title", null)
        return Uri.parse(path)
    }

    private fun uploadImage2(pug: String, breed: String) {

        progressss.visibility = View.VISIBLE
        layout_cat.visibility = View.GONE

        if (mImageCaptureUri != null){

            val ref = storageReference!!.child("Pug/" + UUID.randomUUID().toString())

            ref.putFile(mImageCaptureUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->

                        val Image = uri.toString()
                        addUserToFirestore(pug,breed, Image)
                        Toast.makeText(baseContext, "Upload success...", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addUserToFirestore(pug: String, breed: String, image: String) {

            val dbUser = db!!.collection("Pugs")
            val user = Pog_Moadel(pug, breed, image)
            dbUser.add(user).addOnSuccessListener {
                Toast.makeText(this, "Your Profile is Sucessfully Updated", Toast.LENGTH_LONG).show()

                progressss.visibility = View.GONE
                layout_cat.visibility = View.VISIBLE

                intent = Intent(this, ListAnimal::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener { e ->
                Toast.makeText(this, "Fail to add course \n$e", Toast.LENGTH_SHORT).show()
            }
        }



}