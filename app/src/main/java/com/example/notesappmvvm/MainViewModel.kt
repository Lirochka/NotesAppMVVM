package com.example.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappmvvm.database.room.AppRoomDataBase
import com.example.notesappmvvm.database.room.repository.RoomRepository
import com.example.notesappmvvm.model.Note
import com.example.notesappmvvm.utils.REPOSITORY
import com.example.notesappmvvm.utils.TYPE_FIREBASE
import com.example.notesappmvvm.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    //функция инициализации базы данных в ViewModel
    fun initDataBase(type: String, onSuccess: () -> Unit){
        Log.d ("MyLog", "MainViewModel initDataBase with type: $type")
        when(type) { //тип БД
            TYPE_ROOM -> {
                val dao = AppRoomDataBase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel Class")
    }
}
