package com.muhammadhusniabdillah.challengechapter5.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "2d99547413d65b3689b39d37f94d2049",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "2d99547413d65b3689b39d37f94d2049",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "2d99547413d65b3689b39d37f94d2049",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}