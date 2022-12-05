package com.example.imagecup.data.room

import androidx.room.*
import com.example.imagecup.model.Photo

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo_table GROUP BY label")
    suspend fun getAllLabels():List<Photo>

    @Query("SELECT * FROM photo_table WHERE label = :label")
    suspend fun getPhotos(label : String):List<Photo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photos : List<Photo>)

    @Delete
    suspend fun deletePhoto(photo : Photo)

    @Update
    suspend fun updatePhoto(photo : Photo)


}