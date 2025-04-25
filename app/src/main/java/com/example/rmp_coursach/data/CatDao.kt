package com.example.rmp_coursach.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rmp_coursach.model.Cat

@Dao
interface CatDao {

    @Insert
    fun insert(cat: Cat) // без suspend, чтобы вызывать из Java

    @Insert
    fun insertAll(cats: List<Cat>)

    @Query("SELECT * FROM cats")
    fun getAllCats(): LiveData<List<Cat>>

    @Query("DELETE FROM cats")
    fun clearAll()
}
