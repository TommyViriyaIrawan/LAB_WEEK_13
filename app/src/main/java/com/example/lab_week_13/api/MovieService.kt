package com.example.lab_week_13

import retrofit2.http.GET
import retrofit2.http.Query
import com.example.lab_week_13.model.PopularMoviesResponse
import com.example.lab_week_13.model.Movie
import retrofit2.http.Path



interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie

}
