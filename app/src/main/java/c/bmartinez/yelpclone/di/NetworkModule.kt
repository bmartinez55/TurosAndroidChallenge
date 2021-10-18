package c.bmartinez.yelpclone.di

import android.content.Context
import c.bmartinez.yelpclone.BaseApplication
import c.bmartinez.yelpclone.data.remote.RetrofitApi
import c.bmartinez.yelpclone.data.repository.BusinessRepositoryImpl
import c.bmartinez.yelpclone.di.interceptors.ApiKeyAuthInterceptor
import c.bmartinez.yelpclone.di.interceptors.CacheInterceptor
import c.bmartinez.yelpclone.di.interceptors.ForceCacheInterceptor
import c.bmartinez.yelpclone.di.interceptors.OfflineCacheInterceptor
import c.bmartinez.yelpclone.utils.baseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClientCache(@ApplicationContext context: Context): Cache {
        val cacheSize: Long = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .cache(cache)
            .addNetworkInterceptor(ApiKeyAuthInterceptor())
            .addInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor(BaseApplication().baseContext))
            .addInterceptor(OfflineCacheInterceptor(BaseApplication().baseContext))
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)

    @Singleton
    @Provides
    fun provideBusinessRepository(retrofitApi: RetrofitApi) = BusinessRepositoryImpl(retrofitApi)
}