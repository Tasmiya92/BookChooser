package com.tasmiya.bookchooser.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tasmiya.bookchooser.data.UserEnitiy

@Dao
interface UserDao {

    @Insert
     suspend fun insert( users : UserEnitiy) : Long

    @Update
    suspend fun updateUser(users: UserEnitiy) : Int

    @Query("SELECT * FROM user")
     fun getAllUsers() : LiveData<List<UserEnitiy>>

    @Delete
    suspend fun deleteUsers(users: UserEnitiy) : Int
}