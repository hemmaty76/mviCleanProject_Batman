package com.sis.base.di

import android.app.Application
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.room.Room
import com.sis.base.data.db.DataBase
import com.sis.base.data.remote.ApiService
import com.sis.base.data.remote.networkUtil.CallAdapterFactory
import com.sis.base.utils.API_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * AppModule is to provide di for general used objects
 *
 * @author mojtaba hemmati sis
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): DataBase =
        Room.databaseBuilder(context, DataBase::class.java, "mvi_database")
            .build()

    @Provides
    @Named("BASE_URL")
    @Singleton
    fun provideBseUrl() = API_BASE

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient.Builder {
        val cacheFile = File(context.cacheDir, "httpCache")
        val cache = Cache(cacheFile, (10 * 1000 * 1000).toLong())
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30,  TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(cache)
            .build()
        if (BuildConfig.DEBUG) httpClient.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("version", java.lang.String.valueOf(BuildConfig.VERSION_CODE))
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        return httpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient: OkHttpClient.Builder,
        @Named("BASE_URL") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CallAdapterFactory())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}