package com.kapirti.zikirmatik.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ZikirModel(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name:String?,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description:String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}