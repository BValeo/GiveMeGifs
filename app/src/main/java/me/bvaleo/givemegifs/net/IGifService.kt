package me.bvaleo.givemegifs.net

import io.reactivex.Single
import me.bvaleo.givemegifs.model.GifData
import me.bvaleo.givemegifs.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface IGifService{
    @GET("/v1/gifs/search")
    fun searchByQuery(@Query("q") query: String,  @Query("offset") offset: Int, @Query("api_key") key: String = Constants.API_KEY): Single<GifData>

    @GET("/v1/gifs/trending")
    fun getTrendingGif(@Query("offset") offset: Int = 0, @Query("api_key") key: String = Constants.API_KEY): Single<GifData>
}