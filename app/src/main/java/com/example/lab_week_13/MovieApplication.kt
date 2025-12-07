package com.example.lab_week_13

import android.app.Application
import com.example.lab_week_13.database.MovieDatabase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {

    lateinit var movieRepository: MovieRepository

    override fun onCreate() {
        super.onCreate()

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        // ⬇️ Step 9: buat instance MovieDatabase
        val movieDatabase = MovieDatabase.getInstance(applicationContext)

        // ⬇️ Step 9: pass movieDatabase ke repository
        movieRepository = MovieRepository(movieService, movieDatabase)
    }
}
