package com.example.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter (private val pokeList: List<Data>):RecyclerView.Adapter<PokeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val pokeName: TextView
        val pokeType: TextView
        val pokeAbility: TextView
        init {

            pokeImage = view.findViewById(R.id.poke_image)
            pokeName = view.findViewById(R.id.poke_name)
            pokeType = view.findViewById(R.id.poke_type)
            pokeAbility = view.findViewById(R.id.poke_ability)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = pokeList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = pokeList[position]
        Glide.with(holder.itemView.context)
            .load(data.pokeImageURL)
            .centerCrop()
            .into(holder.pokeImage)
        holder.pokeName.text = data.pokeName
        holder.pokeType.text = data.pokeType
        holder.pokeAbility.text = data.pokeAbility

    }
}