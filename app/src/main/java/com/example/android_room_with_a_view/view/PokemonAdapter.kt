package com.example.android_room_with_a_view.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_room_with_a_view.common.OnPokemonClicked
import com.example.android_room_with_a_view.databinding.ItemLayoutBinding
import com.example.android_room_with_a_view.domain.response.DetailsDomain
import com.example.android_room_with_a_view.domain.response.ResultDomain

class PokemonAdapter(
    private val onPokemonClicked: OnPokemonClicked,
    private val items: MutableList<ResultDomain> = mutableListOf(),
) : RecyclerView.Adapter<PokemonAdapter.UserViewHolder>() {

    class UserViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateData(newItems: List<ResultDomain>) {
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
        holder.binding.heroeTxt.text = items[position].name

        holder.binding.movieView.setOnClickListener {
            onPokemonClicked.pokemonClicked((items[position]))
        }

    }

    override fun getItemCount(): Int = items.size
}