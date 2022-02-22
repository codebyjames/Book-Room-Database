package com.example.bookroomdatabase.viewmodel

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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bookroomdatabase.database.BookDatabase
import com.example.bookroomdatabase.model.Book
import com.example.bookroomdatabase.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class BookViewModel(application: Application) : AndroidViewModel(application) {

    val _listOfBooks = MutableLiveData<List<Book>>()
    val listOfBooks: LiveData<List<Book>> = _listOfBooks

    private val repository: Repository

    init {
        val bookDao = BookDatabase.getDatabase(application).getBookDao()
        repository = Repository(bookDao)
        getAllBooks()
    }

    private fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.listOfBooks.collect {
                withContext(Dispatchers.Main) {
                    _listOfBooks.value = it
                }
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBook(book)
        }
    }

    fun deleteBookByID(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBookByID(id)
        }
    }

    fun deleteAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllBooks()
        }
    }

    fun getBookByID(id: Int): Book =
        runBlocking {
            fetchBookAsync(id)
        }

    private suspend fun fetchBookAsync(id: Int): Book {
        val book = viewModelScope.async(Dispatchers.IO) {
            repository.getBookByID(id)
        }
        return book.await()
    }

}