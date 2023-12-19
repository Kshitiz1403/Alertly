package com.niraj.alertly.DI

import com.niraj.alertly.BuildConfig
import com.niraj.alertly.network.AlertlyAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(
            BuildConfig.BASE_URL
        ).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesNewsApi(retrofit: Retrofit) : AlertlyAPI {
        return retrofit.create(AlertlyAPI::class.java)
    }

}