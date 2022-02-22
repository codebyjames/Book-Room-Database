package com.example.bookroomdatabase.repository

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

import com.example.bookroomdatabase.database.BookDao
import com.example.bookroomdatabase.model.Book
import kotlinx.coroutines.flow.Flow

class Repository(private val bookDao: BookDao) {

    val listOfBooks: Flow<List<Book>> = bookDao.getAllBooks()

    suspend fun addBook(book: Book) {
        bookDao.addBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun deleteBookByID(id: Int) {
        bookDao.deleteByID(id)
    }

    suspend fun deleteAllBooks() {
        bookDao.deleteAllBooks()
    }

    suspend fun getBookByID(id: Int): Book {
        return bookDao.getBookByID(id)
    }

}