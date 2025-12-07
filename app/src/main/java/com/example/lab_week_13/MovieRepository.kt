package com.example.lab_week_13

import android.util.Log
import com.example.lab_week_13.database.MovieDatabase
import com.example.lab_week_13.database.MovieDao
import com.example.lab_week_13.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) {

    private val apiKey = "4f3099dea795f1050f22e95e26c29e4e" // kamu sudah isi 👍

    fun fetchMovies(): Flow<List<Movie>> {
        return flow {
            val movieDao = movieDatabase.movieDao()
            val savedMovies = movieDao.getMovies()

            if (savedMovies.isEmpty()) {
                // Tidak ada di DB → fetch dari API
                val movies = movieService.getPopularMovies(apiKey).results
                // Simpan ke DB
                movieDao.addMovies(movies)
                // Emit data dari API
                emit(movies)
            } else {
                // Ada di DB → langsung emit dari Room
                emit(savedMovies)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun fetchMovieDetails(movieId: Int): Flow<Movie?> {
        return flow {
            val movieDao = movieDatabase.movieDao()
            val movie = movieDao.getMovieById(movieId)
            emit(movie)
        }.flowOn(Dispatchers.IO)
    }

    // fetch movies from the API and save them to the database
    // this function is used at an interval to refresh the list of popular movies
    suspend fun fetchMoviesFromNetwork() {
        val movieDao: MovieDao = movieDatabase.movieDao()
        try {
            val popularMovies = movieService.getPopularMovies(apiKey)
            val moviesFetched = popularMovies.results
            movieDao.addMovies(moviesFetched)
        } catch (exception: Exception) {
            Log.d(
                "MovieRepository",
                "An error occurred: ${exception.message}"
            )
        }
    }

}
