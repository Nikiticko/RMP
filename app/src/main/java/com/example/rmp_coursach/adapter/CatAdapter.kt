package com.example.rmp_coursach.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.rmp_coursach.R
import com.example.rmp_coursach.model.Cat
import com.squareup.picasso.Picasso

class CatAdapter(private var cats: List<Cat>) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    inner class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catImageView: ImageView = itemView.findViewById(R.id.catImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = cats[position]
        Picasso.get().load(cat.imageUrl).into(holder.catImageView)
    }

    override fun getItemCount(): Int = cats.size

    fun updateData(newList: List<Cat>) {
        cats = newList
        notifyDataSetChanged()
    }
}
