package com.tasmiya.bookchooser.view

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tasmiya.bookchooser.R
import com.tasmiya.bookchooser.view.adapter.RecyclerViewAdapter
import com.tasmiya.bookchooser.data.UserDatabase
import com.tasmiya.bookchooser.data.UserEnitiy
import com.tasmiya.bookchooser.databinding.ActivityDataViewBinding
import com.tasmiya.bookchooser.repo.UserRepository
import com.tasmiya.bookchooser.viewmodel.UserViewModel
import com.tasmiya.bookchooser.viewmodel.UserViewModelFactory


class DataViewActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityDataViewBinding
    private lateinit var adapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_view)
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.viewModel = userViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        userViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

    //shows all data
    private fun initRecyclerView() {
        binding.rcvRecipe.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewAdapter({ selectedItem: UserEnitiy -> listItemClicked(selectedItem) })
        binding.rcvRecipe.adapter = adapter
        displayUserList()
    }

    //add data in recyclerview
    private fun displayUserList() {
        userViewModel.users.observe(this, Observer {
            Log.i("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }



   // show dailog on click of recyclerview item
    private fun listItemClicked(user: UserEnitiy){
        Log.d("clicked","selected name is ${user.name}")

            userViewModel.initUpdateAndDelete(user)
        showDialog(user)

    }

    private fun showDialog(user: UserEnitiy) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_layout)
        dialog.setCanceledOnTouchOutside(true)
        val ed_name = dialog.findViewById(R.id.ed_name) as EditText
        ed_name.setText(user.name)
        val ed_number = dialog.findViewById(R.id.editTextPhone) as EditText
        ed_number.setText(user.phone)
        val sp_book = dialog.findViewById(R.id.sp_bookSelect) as Spinner
        sp_book.setSelection((sp_book.getAdapter() as ArrayAdapter<String?>).getPosition(user.bookname))

       val btn_update = dialog.findViewById(R.id.btn_viewAll) as Button
       val btn_delete = dialog.findViewById(R.id.btn_saveData) as Button

        userViewModel.userSelectedBook


        btn_update.setOnClickListener {
           val name=  ed_name.text.toString()
            val phone = ed_number.text.toString()
            val selectedBook = sp_book.selectedItem.toString()
            user.name = name
            user.phone = phone
            user.bookname = selectedBook
            userViewModel.initUpdateAndDelete(user)
                userViewModel.save()
            dialog.dismiss()
        }

        btn_delete.setOnClickListener {
            userViewModel.delete(user)
            dialog.dismiss()
        }
        dialog.show()
    }
}