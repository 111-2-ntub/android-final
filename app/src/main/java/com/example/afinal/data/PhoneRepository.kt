package com.example.afinal.data

import android.util.Log
import androidx.lifecycle.LiveData

class PhoneRepository(private val phoneDao:PhoneDao) {
    val read: LiveData<List<Phone>> = phoneDao.getAll()

    fun addPhone(phone:Phone){
        phoneDao.addPhone(phone)
    }
    fun deleteById(id:Int){
        val d =phoneDao.deleteById(id)
        Log.d("TAG", "repo deleteById: ${d}")
    }

    fun update(phone: Phone){
        phoneDao.update(phone)
    }

    fun delPhone(phone: Phone){
        phoneDao.delete(phone)
    }

}