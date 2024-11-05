package com.example.moviecatalog.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.viewholder.FavoriteViewHolder
import com.example.moviecatalog.app.presentation.viewmodel.FavoritesViewModel
import com.example.moviecatalog.domain.model.MovieElement

class FavoriteAdapter: ListAdapter<MovieElement, FavoriteViewHolder>(DIFF){

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<MovieElement>(){
            override fun areItemsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieElement, newItem: MovieElement): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.favorite_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val movieElement = getItem(position)
        holder.bind(movieElement)
    }
}