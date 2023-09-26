package com.mmm.exam_android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mmm.exam_android.databinding.ActivityIteamviewMainBinding

class IteamviewMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityIteamviewMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIteamviewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var img = intent.getStringExtra("img")
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        var category = intent.getStringExtra("category")
        var price = intent.getStringExtra("price")
        var rateing = intent.getStringExtra("rateing")

        Glide.with(this).load(img).into(binding.img)
        binding.txttitle.text = title
        binding.txtdescription.text = description
        binding.txtcategory.text = category
        binding.txtprice.text = price

        binding.btnback.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}