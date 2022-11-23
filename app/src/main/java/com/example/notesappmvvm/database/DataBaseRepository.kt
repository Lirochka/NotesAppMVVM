package com.example.notesappmvvm.database

import androidx.lifecycle.LiveData
import com.example.notesappmvvm.model.Note

interface DataBaseRepository {

    // считывает все данные из БД
    val readAll: LiveData<List<Note>>

    //создает, обновляет и удаляет заметки
    suspend fun create(note: Note, onSuccess: () -> Unit)

    suspend fun update(note: Note, onSuccess: () -> Unit)

    suspend fun delete(note: Note, onSuccess: () -> Unit)

    fun signOut() { }

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) { }
}