package com.example.musicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val idUser: Int?,
    val email: String,
    val password: String,
    val username: String,
    val address: String,

    )

