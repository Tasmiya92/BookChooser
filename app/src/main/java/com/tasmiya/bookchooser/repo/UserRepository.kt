package com.tasmiya.bookchooser.repo

import com.tasmiya.bookchooser.data.UserDao
import com.tasmiya.bookchooser.data.UserEnitiy

class UserRepository(private val dao : UserDao) {

    val users = dao.getAllUsers()

    suspend fun insert(user : UserEnitiy){

        dao.insert(user)
    }

    suspend fun update(user : UserEnitiy){
        dao.updateUser(user)
    }

    suspend fun delete(user : UserEnitiy){
        dao.deleteUsers(user)
    }
}