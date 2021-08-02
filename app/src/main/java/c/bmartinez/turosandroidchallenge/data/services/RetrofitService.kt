package c.bmartinez.turosandroidchallenge.data.services

import c.bmartinez.turosandroidchallenge.data.model.YelpSearchBeerResults
import c.bmartinez.turosandroidchallenge.data.model.YelpSearchPizzaResults
import c.bmartinez.turosandroidchallenge.utils.YelpConstants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {

    @GET("businesses/search/")
    suspend fun getAllPizzaLocations(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String
    ): Response<List<YelpSearchPizzaResults>>

    @GET("businesses/search/")
    suspend fun getAllBeerLocations(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String
    ): Response<List<YelpSearchBeerResults>>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(YelpConstants().baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}