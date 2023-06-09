package com.example.afinal.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PhoneDao {
    @Query("SELECT * FROM phone")
    fun getAll(): LiveData<List<Phone>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhone(vararg users: Phone)

    @Update
    fun update(phone:Phone)

    @Query("DELETE FROM phone WHERE id = :id")
    fun deleteById(id: Int):Int

    @Delete
    fun delete(user: Phone)
}