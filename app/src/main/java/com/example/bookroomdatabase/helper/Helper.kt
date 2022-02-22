package com.example.bookroomdatabase.helper

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

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast

object Helper {

    /**
     * Check input if empty
     */
    fun inputCheck(
        title: String,
        year: String,
        firstName: String,
        lastName: String,
        country: String
    ): Boolean {
        return !(
                TextUtils.isEmpty(title) &&
                        TextUtils.isEmpty(year) &&
                        TextUtils.isEmpty(firstName) &&
                        TextUtils.isEmpty(lastName) &&
                        TextUtils.isEmpty(country)
                )
    }


    /**
     * Get the String from TextView / EditText
     */
    fun textValue(textView: TextView): String {
        return textView.text.toString()
    }

    /**
     * Show Delete Alert Dialog
     * when user wants to press delete menu option
     */
    fun showDeleteDialog(context: Context, title: String, message: String,
                         actionDelete: () -> Unit, navigateAction: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") {_, _ ->
            actionDelete()
            Toast.makeText(context,"Delete Success!", Toast.LENGTH_SHORT).show()
            navigateAction()
        }
        builder.setNegativeButton("No") {_, _ ->}
        builder.setTitle(title)
        builder.setMessage(message)
        builder.create().show()
    }

}