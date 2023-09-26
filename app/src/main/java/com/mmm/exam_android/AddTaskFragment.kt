package com.kevin.taskmanagement.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mmm.exam_android.R
import com.mmm.exam_android.ShoppingModel
import com.mmm.exam_android.databinding.FragmentAddTaskBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddTaskFragment : Fragment() {

    lateinit var binding: FragmentAddTaskBinding
    lateinit var uri: Uri
    val IMAGE_CODE = 8
    lateinit var storageRef: StorageReference
    lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)

        dbRef = FirebaseDatabase.getInstance().reference
        storageRef = FirebaseStorage.getInstance().reference

        binding.addimg.setOnClickListener {

            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_CODE)
        }

        binding.uploaddata.setOnClickListener {

            val ref = storageRef.child("images/${uri.lastPathSegment}.jpg")
            var uploadTask = ref.putFile(uri)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    if (binding.producttitle.text.toString().isEmpty() || binding.productdesc.text.toString().isEmpty() || binding.productcategory.text.toString().isEmpty() || binding.productprice.text.toString().isEmpty()
                    ) {
                        Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
                    } else {
                        val downloadUri = task.result

                        var key = dbRef.root.push().key
                        var title = binding.producttitle.text.toString()
                        var desc = binding.productdesc.text.toString()
                        var category = binding.productcategory.text.toString()
                        var price = binding.productprice.text.toString()
                        var image = downloadUri.toString()
                        var data =
                            ShoppingModel(key!!, title, desc, category, price, image)

                        dbRef.root.child("Shopping").child(key).setValue(data)
                        Toast.makeText(context, "Data Upload Succesfully", Toast.LENGTH_SHORT).show()
                        binding.producttitle.setText("")
                        binding.productdesc.setText("")
                        binding.productcategory.setText("")
                        binding.productprice.setText("")
                        binding.imgposter.setImageResource(R.drawable.imageadd)
                    }
                } else {

                }
            }
        }

        return binding.root


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == IMAGE_CODE) {
                uri = data?.data!!
                binding.imgposter.setImageURI(uri)
            }
        }
    }


}