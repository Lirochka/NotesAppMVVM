package com.example.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappmvvm.model.Note
import com.example.notesappmvvm.utils.TYPE_FIREBASE
import com.example.notesappmvvm.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application): AndroidViewModel(application) {

    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }
    //хранит тип базы данных
    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }
    //список заметок
    init {
        readTest.value =
            when(dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note 1", subTitle = "Subtitle for note 1"),
                        Note(title = "Note 2", subTitle = "Subtitle for note 2"),
                        Note(title = "Note 3", subTitle = "Subtitle for note 3"),
                        Note(title = "Note 4", subTitle = "Subtitle for note 4")
                    )
                }
                TYPE_FIREBASE -> listOf()
                else -> listOf()
            }
    }
    //функцию инициализации базы данных в ViewModel
    fun initDataBase(type: String){
      dbType.value = type  //присваиваем тип базы данных, при click на startScreen
        Log.d ("MyLog", "MainViewModel initDataBase with type: $type")
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
