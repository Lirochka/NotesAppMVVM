package com.example.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.notesappmvvm.database.room.AppRoomDataBase
import com.example.notesappmvvm.database.room.repository.RoomRepository
import com.example.notesappmvvm.model.Note
import com.example.notesappmvvm.utils.REPOSITORY
import com.example.notesappmvvm.utils.TYPE_FIREBASE
import com.example.notesappmvvm.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    //функция инициализации базы данных в ViewModel
    fun initDataBase(type: String, onSuccess: () -> Unit) {
        Log.d("MyLog", "MainViewModel initDataBase with type: $type")
        when (type) { //тип БД
            TYPE_ROOM -> {
                val dao = AppRoomDataBase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }


    //добавление заметки в coroutine в IO потоке
    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    //обновление заметки
    fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    //удаление заметки
    fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    //функция вывода заметок на MainScree после сохранения в AddScreen
    fun readAllNotes() = REPOSITORY.readAll
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
