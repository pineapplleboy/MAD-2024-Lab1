package com.example.moviecatalog.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviecatalog.R
import com.example.moviecatalog.app.presentation.viewholder.MovieCardViewHolder
import com.example.moviecatalog.domain.model.MovieElement

class MovieCardAdapter: ListAdapter<MovieElement, MovieCardViewHolder>(DIFF){

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCardViewHolder {
        return MovieCardViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        val movieElement = getItem(position)
        holder.bind(movieElement)
    }
}