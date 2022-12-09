/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
//digunakan untuk mendapatkan video dari data base
@Dao
// berfungsi sebagai database class object
interface VideoDao {
    // menampilkan data
    //digunakan untuk mendapatkan data menghasilkan list data base video
    @Query("select * from databasevideo")
    // live data
    fun getVideos(): LiveData<List<DatabaseVideo>>
    // digunakn untuk menambahkan data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // digunakan untuk menginstal semua video yang akan ditampilkan
    fun insertAll( videos: List<DatabaseVideo>)
}


// merupakan kelas  DatabaseVideo merupakan RoomDb
@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    // digunakan untuk mengakses method dao
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
    synchronized(VideosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            // instance room database
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    VideosDatabase::class.java,
                    "videos").build()
        }
    }
    return INSTANCE
}
