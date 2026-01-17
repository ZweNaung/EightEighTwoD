package com.example.eighteighttwod.di

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.eighteighttwod.data.remote.api.LiveApiService
import com.example.eighteighttwod.utils.Constants // BASE_URL ရှိတဲ့နေရာ
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // App တစ်ခုလုံးမှာ သုံးမှာမို့ SingletonComponent သုံးတယ်
object NetworkModule {

    // ၁။ Json Configuration ကို Provide လုပ်မယ်
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    // ၂။ OkHttpClient ကို Provide လုပ်မယ်
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    //၁။ Retrofit အကြီးကို အရင် ဆောက်ပေးလိုက်မယ် (ApiService မဟုတ်တော့ဘူး)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    //၂။ ညီလေးလိုချင်တဲ့ LiveApiService ကို ဒီမှာ သီးသန့် ထုတ်ပေးမယ်
    @Provides
    @Singleton
    fun provideLiveApiService(retrofit: Retrofit): LiveApiService {
        return retrofit.create(LiveApiService::class.java)
    }

    // နောက်ပိုင်း TwoDHistory လိုချင်ရင် ဒီမှာ ထပ်ဖြည့်ရုံပဲ.
    // @Provides
    // @Singleton
    // fun provideTwoDApiService(retrofit: Retrofit): TwoDHistoryApiService {
    //     return retrofit.create(TwoDHistoryApiService::class.java)
    // }
}