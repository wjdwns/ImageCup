package com.example.imagecup.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class Photo(
    @PrimaryKey val title : String,
    @ColumnInfo(name = "label") val label : String
)
