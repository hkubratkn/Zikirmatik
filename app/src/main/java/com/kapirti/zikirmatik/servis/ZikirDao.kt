package com.kapirti.zikirmatik.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kapirti.zikirmatik.model.ZikirModel

@Dao
interface ZikirDao {

    @Insert
    suspend fun insertAll(vararg zikir:ZikirModel):List<Long>

    @Query("SELECT * FROM ZikirModel")
    suspend fun getAllZikir():List<ZikirModel>

    @Query("DELETE FROM ZikirModel")
    suspend fun deleteAllZikir()
}