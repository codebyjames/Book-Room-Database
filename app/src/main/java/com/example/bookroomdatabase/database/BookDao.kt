package com.example.bookroomdatabase.database

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

import androidx.room.*
import com.example.bookroomdatabase.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Query("delete from books")
    suspend fun deleteAllBooks()

    @Query("delete from books where id = :id")
    suspend fun deleteByID(id: Int)

    @Query("select * from books order by id asc")
    fun getAllBooks() : Flow<List<Book>>

    @Query("select * from books where id = :id limit 1")
    suspend fun getBookByID(id: Int) : Book

}