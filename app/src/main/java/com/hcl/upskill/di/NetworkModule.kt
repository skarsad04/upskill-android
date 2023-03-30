package com.hcl.upskill.di

import com.hcl.upskill.repository.remote.Skill
import com.hcl.upskill.repository.remote.SkillConstant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Reusable
    internal fun provideSkillClient(builder: OkHttpClient.Builder) : Skill {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build().create(Skill::class.java)
    }

    @Provides
    @Reusable
    internal fun provideOkhttpClientBuilder(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(50, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
    }


}