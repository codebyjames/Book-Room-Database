package com.example.bookroomdatabase.fragments

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

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookroomdatabase.R
import com.example.bookroomdatabase.databinding.FragmentAddBinding
import com.example.bookroomdatabase.helper.Helper.inputCheck
import com.example.bookroomdatabase.helper.Helper.textValue
import com.example.bookroomdatabase.model.Author
import com.example.bookroomdatabase.model.Book
import com.example.bookroomdatabase.viewmodel.BookViewModel

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        binding.btnAdd.setOnClickListener {
            addBookToDatabase()
        }
        return binding.root
    }

    private fun addBookToDatabase() {
        binding.apply {

            val title = textValue(etTitle)
            val year = textValue(etYear)
            val firstName = textValue(etAuthorFirstName)
            val lastName = textValue(etAuthorLastName)
            val country = textValue(etAuthorCountry)

            if (inputCheck(title, year, firstName, lastName, country)) {
                val book = Book(0, title, Integer.parseInt(year), Author(firstName, lastName, country))
                bookViewModel.addBook(book)
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }

        }
    }

}