package com.kevin.taskmanagement.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.mmm.exam_android.ItemAdapter
import com.mmm.exam_android.ShoppingModel
import com.mmm.exam_android.databinding.FragmentHomeBinding
import io.grpc.internal.JsonUtil.getList

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    var adapter = ItemAdapter()
    var shoppinglist = ArrayList<ShoppingModel>()
    lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)


        getList()

        binding.rcvtasklist.layoutManager = GridLayoutManager(context, 2)
        binding.rcvtasklist.adapter = adapter
        adapter.update(shoppinglist)

        return binding.root


    }

    private fun getList() {
        dbRef = FirebaseDatabase.getInstance().getReference("Shopping")

        dbRef.root.child("Shopping").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                shoppinglist.clear()
                for (snap in snapshot.children) {
                    var model = snap.getValue(ShoppingModel::class.java)
                    shoppinglist.add(model!!)
                }
                adapter.update(shoppinglist)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}