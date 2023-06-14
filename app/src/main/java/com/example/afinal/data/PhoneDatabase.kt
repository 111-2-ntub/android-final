package com.example.afinal.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec


// Database class after the version update.
@Database(
    version = 4,
    entities = [Phone::class],
    autoMigrations = [
        AutoMigration (from = 3, to = 4,
                spec = PhoneDatabase.MyAutoMigration::class
        ),

    ],
    exportSchema = true,

)

abstract class PhoneDatabase : RoomDatabase() {
    class MyAutoMigration : AutoMigrationSpec
    abstract fun phoneDao(): PhoneDao
    companion object {
        @Volatile
        private var INSTANCE: PhoneDatabase? = null

        fun getDatabase(context: Context): PhoneDatabase {
            val tempInstance= INSTANCE
            if (tempInstance!=null) return  tempInstance
            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,PhoneDatabase::class.java,"phone"
                ).allowMainThreadQueries().build()
                INSTANCE=instance
                return instance

            }
        }


    }
}