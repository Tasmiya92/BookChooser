package com.tasmiya.bookchooser.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasmiya.bookchooser.util.Event
import com.tasmiya.bookchooser.repo.UserRepository
import com.tasmiya.bookchooser.data.UserEnitiy
import kotlinx.coroutines.launch


class UserViewModel(private val repository: UserRepository) : ViewModel(),Observable {
    val users = repository.users

    var userSelectedBook : String = ""

    private var isUpdateOrDelete = false

    private lateinit var userUpdateOrDelete: UserEnitiy


    @Bindable
    val userName = MutableLiveData<String>()

    @Bindable
    val userNumber = MutableLiveData<String>()

    @Bindable
    val saveorUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val DeleteButtonText = MutableLiveData<String>()


    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage


    val clicksListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            userSelectedBook = parent?.getItemAtPosition(position) as String
            Log.d("dddd",userSelectedBook)

            val books = userSelectedBook
            Log.d("add",books)
        }
    }

    init{
        saveorUpdateButtonText.value = "Save"
        DeleteButtonText.value = "Delete"

    }


    fun save(){
        if (userName.value == null) {
            statusMessage.value = Event("Please enter User name")
        } else if (userNumber.value == null) {
            statusMessage.value = Event("Please enter User PhoneNumber")

        } else {
            if (isUpdateOrDelete) {
               userUpdateOrDelete.name = userName.value!!
                userUpdateOrDelete.phone = userNumber.value!!
                userUpdateOrDelete.bookname=userSelectedBook
                update(userUpdateOrDelete)
            } else {
                val name = userName.value!!
                val phone = userNumber.value!!
                val book = userSelectedBook
                insert(UserEnitiy(0, name, phone,book))
                userName.value = null
                userNumber.value = null
            }
        }
    }

    fun insert(user : UserEnitiy){
        viewModelScope.launch {
             val newRowId = repository.insert(user)
            statusMessage.value = Event("Row Inserted Successfully")
        }
    }

    fun update(user : UserEnitiy) = viewModelScope.launch {
        val noOfRows = repository.update(user)
            userName.value = null
            userNumber.value = null
            isUpdateOrDelete = false
        statusMessage.value = Event("Row Updated Successfully")


    }

    fun delete(user: UserEnitiy) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(user)
            userName.value = null
            userNumber.value = null
            isUpdateOrDelete = false
            statusMessage.value = Event("Row Deleted Successfully")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    fun initUpdateAndDelete(user: UserEnitiy) {
        userName.value = user.name
        userNumber.value = user.phone
        userSelectedBook = user.bookname.toString()
        isUpdateOrDelete = true
        userUpdateOrDelete = user

    }
}

private operator fun Any.compareTo(i: Int): Int {
    return i

}






