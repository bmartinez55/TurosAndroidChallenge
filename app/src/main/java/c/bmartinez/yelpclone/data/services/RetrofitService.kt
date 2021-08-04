package c.bmartinez.yelpclone.data.services

import c.bmartinez.yelpclone.data.model.*
import c.bmartinez.yelpclone.utils.YelpConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/*
    Interface that holds all endpoint calls
 */
interface RetrofitService {

    @GET("businesses/search")
    suspend fun getSearchResults(
        @Query("term") searchTerm: String,
        @Query("location") location: String,
        @Query("radius") searchRadius: Int,
        @Query("sort_by") sortBy: String
    ): Response<YelpSearchResults>

    @GET("businesses/search")
    suspend fun getAllPizzaLocations(
        @Query("term") searchTerm: String,
        @Query("location") location: String,
        @Query("radius") searchRadius: Int,
        @Query("sort_by") sortBy: String
    ): Response<YelpSearchResults>

    @GET("businesses/search")
    suspend fun getAllBeerLocations(
        @Query("term") searchTerm: String,
        @Query("location") location: String,
        @Query("radius") searchRadius: Int
    ): Response<YelpSearchResults>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val original = chain.request()

                //Request customization add request headers
                val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer ${YelpConstants().api_key}")

                val request = requestBuilder.build()
                chain.proceed(request)
            }

            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.addNetworkInterceptor(logging)

            val okHttpClient = httpClient.build()

            if(retrofitService == null){
                val retrofit = Retrofit.Builder()
                    .baseUrl(YelpConstants().baseURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}