package com.example.notesappmvvm.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesappmvvm.model.Note

@Dao
interface NoteRoomDao {

    //указываем функции с к-ыми используются при работе с локальной БД
    //получение всех заметок
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>

    //добавление заметки
    @Insert
    suspend fun addNote(note: Note)
    //обновление заметки
    @Update
    suspend fun updateNote(note: Note)
    //удаление заметок
    @Delete
    suspend fun deleteNote(note: Note)

}