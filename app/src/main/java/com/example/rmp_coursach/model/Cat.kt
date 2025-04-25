package com.example.rmp_coursach.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Room сам сгенерирует значение

    val imageUrl: String // URL картинки
)
