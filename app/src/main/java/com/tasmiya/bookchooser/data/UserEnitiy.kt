package com.tasmiya.bookchooser.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
 data class UserEnitiy(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String?,
    var phone: String?,
    var bookname : String?
) {
}