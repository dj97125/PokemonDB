package com.example.android_room_with_a_view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_room_with_a_view.databinding.ItemLayoutBinding
import com.example.android_room_with_a_view.domain.response.DataDomain
import com.example.android_room_with_a_view.domain.response.UserDomain
import com.squareup.picasso.Picasso

class UserAdapter(
    private val items: MutableList<DataDomain> = mutableListOf(),
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateData(newItems: List<DataDomain>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun erraseData() {
        items.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Picasso.get()
            .load(items[position].avatar)
            .into(holder.binding.moviePoster)
        holder.binding.heroeTxt.text = items[position].firstName

    }

    override fun getItemCount(): Int = items.size
}