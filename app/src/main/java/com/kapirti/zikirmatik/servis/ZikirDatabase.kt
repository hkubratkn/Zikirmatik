package com.kapirti.zikirmatik.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kapirti.zikirmatik.model.ZikirModel

@Database(entities = arrayOf(ZikirModel::class),version = 1)
abstract class ZikirDatabase :RoomDatabase(){

    abstract fun zikirDao():ZikirDao

    companion object{
        @Volatile private var instance:ZikirDatabase?=null
        private val lock=Any()
        operator fun invoke(context:Context)=instance?:synchronized(lock){
            instance?:createDatabase(context).also{
                instance=it
            }
        }

        private fun createDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            ZikirDatabase::class.java,"zikirdatabase").build()

        }
}