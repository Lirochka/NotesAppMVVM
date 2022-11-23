package com.example.notesappmvvm.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesappmvvm.database.room.dao.NoteRoomDao
import com.example.notesappmvvm.model.Note


//создаем базу данных Room
@Database(entities = [Note::class], version = 1)
abstract class AppRoomDataBase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDataBase? = null

        //функция получения INSTANCE БД
        fun getInstance(context: Context): AppRoomDataBase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDataBase::class.java,
                    "notes_database"
                ).build()
                INSTANCE as AppRoomDataBase
            } else INSTANCE as AppRoomDataBase
        }
    }
}