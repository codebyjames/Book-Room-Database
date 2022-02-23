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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookroomdatabase.R
import com.example.bookroomdatabase.adapter.BookAdapter
import com.example.bookroomdatabase.databinding.FragmentListBinding
import com.example.bookroomdatabase.helper.Helper.showDeleteDialog
import com.example.bookroomdatabase.viewmodel.BookViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        val bookAdapter = BookAdapter()
        binding.apply {
            rcvFragList.layoutManager = LinearLayoutManager(requireContext())
            rcvFragList.adapter = bookAdapter
            floatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }
        setHasOptionsMenu(true)

        /**
         * Option 1 : use LiveData from ViewModel
         */
        bookViewModel.listOfBooks.observe(viewLifecycleOwner, Observer { list ->
            bookAdapter.submitList(list)
        })

        /**
         * Option 2 : use Flow directly instead of LiveData through ViewModel
         * need to make repository public in BookViewModel file
         */
//        lifecycleScope.launch {
//            bookViewModel.repository.listOfBooks.collect {
//                bookAdapter.submitList(it)
//            }
//        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.option_delete) {
            deleteBooks()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteBooks() {
        showDeleteDialog(
            requireContext(),
            "Delete All",
            "Are you sure you want to delete all the books?",
        {bookViewModel.deleteAllBooks()}, {})
    }

}