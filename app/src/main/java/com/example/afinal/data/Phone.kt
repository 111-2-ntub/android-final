package com.example.afinal.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "phone")
data class Phone(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name="bathday") val bathday:String?,
    @ColumnInfo (name= "photo") val image: String?
)