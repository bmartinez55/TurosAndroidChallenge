package c.bmartinez.yelpclone.di

import android.content.Context
import c.bmartinez.yelpclone.BaseApplication
import c.bmartinez.yelpclone.data.remote.RetrofitApi
import c.bmartinez.yelpclone.data.repository.BusinessRepositoryImpl
import c.bmartinez.yelpclone.domain.repository.BusinessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRetrofitApi(retrofit: Retrofit): RetrofitApi = retrofit.create(RetrofitApi::class.java)

    @Singleton
    @Provides
    fun provideBusinessRepository(retrofitApi: RetrofitApi): BusinessRepository {
        return BusinessRepositoryImpl(retrofitApi)
    }
}