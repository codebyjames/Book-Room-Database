package com.example.bookroomdatabase.adapter

/*
 * Copyright (C) 2022 James Loboda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookroomdatabase.databinding.RowItemBinding
import com.example.bookroomdatabase.fragments.ListFragmentDirections
import com.example.bookroomdatabase.model.Book

class BookAdapter : ListAdapter<Book, BookAdapter.BookViewHolder>(DiffCallback) {

    lateinit var binding: RowItemBinding
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = RowItemBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = getItem(position)
        binding.apply {
            txtId.text = currentBook.id.toString()
            txtTitle.text = currentBook.title
            txtYear.text = currentBook.year.toString()

            val firstName = currentBook.author.firstName
            val lastName = currentBook.author.lastName
            val country = currentBook.author.country
            txtAuthorName.text = "$firstName, $lastName"
            txtAuthorCountry.text = country


            // click
            root.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentBook.id)
                holder.itemView.findNavController().navigate(action)
            }

        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

        }
    }

}