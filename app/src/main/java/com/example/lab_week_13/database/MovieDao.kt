package com.example.lab_week_13.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab_week_13.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun getMovies(): List<Movie>

    // ➕ TAMBAHAN: ambil detail film berdasarkan id
    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    fun getMovieById(id: Int): Movie?
}

