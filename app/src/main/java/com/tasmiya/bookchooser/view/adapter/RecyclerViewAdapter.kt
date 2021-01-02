package com.tasmiya.bookchooser.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tasmiya.bookchooser.R
import com.tasmiya.bookchooser.data.UserEnitiy
import com.tasmiya.bookchooser.databinding.RecyclerViewItemBinding

class RecyclerViewAdapter(private val clickListener: (UserEnitiy) -> Unit)  : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    private val userList = ArrayList<UserEnitiy>()
    class MyViewHolder( val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEnitiy, clickListener:(UserEnitiy) -> Unit){
            binding.tvName.text = user.name
            binding.tvNumber.text = user.phone
            binding.tvBook.text = user.bookname
            binding.listItemLayout.setOnClickListener{
                clickListener(user)
        }

    }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : RecyclerViewItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.recycler_view_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position],clickListener)
    }


    fun setList(subscribers: List<UserEnitiy>) {
        userList.clear()
        userList.addAll(subscribers)

    }
}