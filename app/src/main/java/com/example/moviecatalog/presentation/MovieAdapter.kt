package com.example.moviecatalog.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.model.MovieElement

class MovieListAdapter : ListAdapter<MovieElement,MovieViewHolder>(DIFF){

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.movie_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieElement = getItem(position)
        holder.bind(movieElement)
    }
}