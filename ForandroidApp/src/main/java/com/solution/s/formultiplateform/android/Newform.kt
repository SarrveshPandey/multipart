package com.solution.s.formultiplateform.android

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_newform.*
import java.io.ByteArrayOutputStream
import java.util.*


open class Newform : AppCompatActivity() {

    private val TAG = "onActivityResult"
    private var db: FirebaseFirestore? = null
    var mImageCaptureUri: Uri? = null

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null


    fun getImage(inContext: Context, photo: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        photo!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, photo, "Title", null)
        return Uri.parse(path)
    }

  override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
            mImageCaptureUri = getImage(this, photo)
            iv_editimg.setImageBitmap(photo)
            iv_editimg.visibility = ImageView.VISIBLE
        } else if (requestCode == 1234 && resultCode == RESULT_OK) {
            if (data != null) {
                mImageCaptureUri = data.data
            }
            /*String Path=mImageCaptureUri.getPath();*/
            iv_editimg.setImageURI(mImageCaptureUri)
            iv_editimg.visibility = ImageView.VISIBLE
        }
    }
    //for get image to gallery and camera



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newform)

        storage = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        db = FirebaseFirestore.getInstance()

        tv_changephoto.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Firebase")
            builder.setMessage("Chose from")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Camera") { dialogInterface, which ->
                intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 101)
            }

            //performing negative action
            builder.setNegativeButton("Gallery") { dialogInterface, which ->
                intent = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
                )
//                val intent ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(intent, 1234)
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }



        btn_savechange.setOnClickListener(View.OnClickListener {

            val Name = et_editname.text.toString()
            val Bio = et_editbio.text.toString()
            val Email = et_email.text.toString()
            val Phone = et_phone.text.toString()
            val Password = et_Password.text.toString()

            when {
                Name.isEmpty() -> {
                    et_editname.error = "Please enter Name"
                }
                Email.isEmpty() -> {
                    et_email.error = "Please enter Email"
                }
                Phone.isEmpty() -> {
                    et_phone.error = "Please enter Phone Number"
                }
                Bio.isEmpty() -> {
                    et_editbio.error = "Please enter Phone Number"
                }
                Password.isEmpty() -> {
                    et_Password.error = "Please enter Password Number"
                }
                else -> {
        //                btn_savechange.setClickable(false);
        //                btn_savechange.setEnabled(false);

                    uploadImage(Name, Email, Phone, Password, Bio)
                }
            }
        })
    }



    fun uploadImage(Name: String, Email: String, Phone: String, Password: String, Bio: String) {

            if (mImageCaptureUri != null){

                var ref = storageReference!!.child("Post/" + UUID.randomUUID().toString())


                ref.putFile(mImageCaptureUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->

                            var Image = uri.toString()

                            addUserToFirestore(Name, Email, Phone, Password, Bio,Image)
                            Toast.makeText(baseContext, "Upload success...", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                    }
            }
    }


    private fun addUserToFirestore(Name: String, Email: String, Phone: String, Password: String, Bio: String, Image: String) {

        val dbUser = db!!.collection("User").document("data")
        val user = User(Name, Email, Phone, Password, Bio, Image)
        dbUser.set(user).addOnSuccessListener {
            Toast.makeText(this, "Your Profile is Sucessfully Updated", Toast.LENGTH_LONG).show()

            intent = Intent(this, ListAnimal::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { e ->
            Toast.makeText(this, "Fail to add course \n$e", Toast.LENGTH_SHORT).show()
        }
    }


}