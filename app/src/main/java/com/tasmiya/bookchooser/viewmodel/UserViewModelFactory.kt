package com.tasmiya.bookchooser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tasmiya.bookchooser.repo.UserRepository

class UserViewModelFactory(private  val respository : UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(respository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}


