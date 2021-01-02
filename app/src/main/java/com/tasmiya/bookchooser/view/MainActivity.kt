package com.tasmiya.bookchooser.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tasmiya.bookchooser.R
import com.tasmiya.bookchooser.data.UserDatabase
import com.tasmiya.bookchooser.databinding.ActivityMainBinding
import com.tasmiya.bookchooser.repo.UserRepository
import com.tasmiya.bookchooser.viewmodel.UserViewModel
import com.tasmiya.bookchooser.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = UserDatabase.getInstance(application).userDao
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this,factory).get(UserViewModel::class.java)
        binding.myViewModel = userViewModel
        binding.lifecycleOwner = this

        displayUserList()
        
        btn_viewAll.setOnClickListener {
           val intent = Intent(this, DataViewActivity::class.java)
            startActivity(intent)
        }

        userViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()

              /*  fun Context.toast(it) =
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()*/
                Log.d("msg", it)
            }
        })

    }
    private fun displayUserList(){
        userViewModel.users.observe(this, Observer {
            Log.i("MYTAG",it.toString())

        })
    }

}