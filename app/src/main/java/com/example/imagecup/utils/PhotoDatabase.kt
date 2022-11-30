package com.example.imagecup.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagecup.data.room.PhotoDao
import com.example.imagecup.model.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    companion object {
        /* @Volatile = 접근가능한 변수의 값을 cache를 통해 사용하지 않고
        thread가 직접 main memory에 접근 하게하여 동기화. */
        @Volatile
        private var instance: PhotoDatabase? = null

//        // 싱글톤으로 생성 (자주 생성 시 성능 손해). 이미 존재할 경우 생성하지 않고 바로 반환
//        fun getDatabase(context: Context): PhotoDatabase? {
//            if (instance == null) {
//                synchronized(PhotoDatabase::class) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        PhotoDatabase::class.java,
//                        "user_database"
//                    ).build()
//                }
//            }
//            return instance
//        }
    }
}