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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookroomdatabase.model.Author
import com.example.bookroomdatabase.model.Book
import com.example.bookroomdatabase.viewmodel.BookViewModel
import com.example.bookroomdatabase.R
import com.example.bookroomdatabase.databinding.FragmentUpdateBinding
import com.example.bookroomdatabase.helper.Helper.inputCheck
import com.example.bookroomdatabase.helper.Helper.showDeleteDialog
import com.example.bookroomdatabase.helper.Helper.textValue

class UpdateFragment : Fragment() {

    lateinit var binding: FragmentUpdateBinding
    lateinit var bookViewModel: BookViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var currentBook: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        binding.btnUpdate.setOnClickListener {
            updateCurrentBook()
        }
        setHasOptionsMenu(true)
        displayCurrentBook()
        return binding.root
    }

    private fun displayCurrentBook() {
        currentBook = bookViewModel.getBookByID(args.id)
        binding.apply {
            currentBook.apply {
                etTitle.setText(title)
                etYear.setText(year.toString())
                etAuthorFirstName.setText(author.firstName)
                etAuthorLastName.setText(author.lastName)
                etAuthorCountry.setText(author.country)
            }
        }
    }

    private fun updateCurrentBook() {
        binding.apply {
            val title = textValue(etTitle)
            val year = textValue(etYear)
            val firstName = textValue(etAuthorFirstName)
            val lastName = textValue(etAuthorLastName)
            val country = textValue(etAuthorCountry)

            if (inputCheck(title, year, firstName, lastName, country)) {
                currentBook = Book(currentBook.id, title, Integer.parseInt(year), Author(firstName, lastName, country))
                bookViewModel.updateBook(currentBook)
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.option_delete) {
            deleteBook()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteBook() {
        showDeleteDialog(requireContext(),
            "Delete Book",
            "Are you sure you want to delete ${currentBook.title}?",
            {bookViewModel.deleteBookByID(args.id)},
            {findNavController().navigate(R.id.action_updateFragment_to_listFragment)})
    }
}