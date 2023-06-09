package com.example.afinal.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData


class PhoneViewModel(application: Application) : AndroidViewModel(application) {
     val getAll: LiveData<List<Phone>>
    private val repository: PhoneRepository

    init {
        val phoneDao: PhoneDao = PhoneDatabase.getDatabase(application).phoneDao()
        repository= PhoneRepository(phoneDao)
        getAll=repository.read
    }


     fun addPhone(phone:Phone){
       repository.addPhone(phone)
    }
    fun deleteById(id: Int){
        Log.d("TAG", "deleteById: before${id}")
        repository.deleteById(id)
        Log.d("TAG", "deleteById: after${id}")
    }
    fun delPhone(phone: Phone){
        repository.delPhone(phone)
    }
    fun update(phone: Phone){
        repository.update(phone)
    }
}