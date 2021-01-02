package com.tasmiya.bookchooser.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEnitiy::class],
    version = 1,
    exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao

    companion object{
        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getInstance(context : Context):UserDatabase{
            synchronized(this){
                var instance : UserDatabase? = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java,
                            "users"
                    ).build()
                }
                return instance

            }

        }


    }
}