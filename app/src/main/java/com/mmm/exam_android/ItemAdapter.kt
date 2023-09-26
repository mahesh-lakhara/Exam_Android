package com.mmm.exam_android

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mmm.exam_android.databinding.ShopingitemBinding

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {

    lateinit var context: Context
    var shoppinglist = ArrayList<ShoppingModel>()

    class ItemHolder(itemView: ShopingitemBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        var binding = ShopingitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return shoppinglist.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.binding.apply {
            shoppinglist.get(position).apply {
                Glide.with(context).load(image).into(imgposter)
                txttitle.text = title
                txtdescription.text = description
                txtcategory.text = category
                txtprice.text = price
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, IteamviewMainActivity::class.java)

                intent.putExtra("img", shoppinglist.get(position).image)
                intent.putExtra("title", shoppinglist.get(position).title)
                intent.putExtra("description", shoppinglist.get(position).description)
                intent.putExtra("category", shoppinglist.get(position).category)
                intent.putExtra("price", shoppinglist.get(position).price)

                it.context.startActivity(intent)
            }
        }
    }

    fun update(shoppinglist: ArrayList<ShoppingModel>) {
        this.shoppinglist = shoppinglist
        notifyDataSetChanged()
    }
}