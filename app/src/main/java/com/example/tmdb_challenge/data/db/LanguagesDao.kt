package com.example.tmdb_challenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.tmdb_challenge.domain.models.entitities.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguagesDao {
    @Insert(onConflict = REPLACE)
    fun insertAll(languages: List<LanguageEntity>)

    @Insert
    fun insert(language: LanguageEntity)

    @Query("SELECT * FROM languages")
    fun getAll(): Flow<List<LanguageEntity>>
}