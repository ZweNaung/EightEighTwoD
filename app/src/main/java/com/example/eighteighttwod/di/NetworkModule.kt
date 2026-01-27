package com.example.eighteighttwod.di

import android.annotation.SuppressLint
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.example.eighteighttwod.data.remote.api.LiveApiService
import com.example.eighteighttwod.data.remote.api.LuckyApiService
import com.example.eighteighttwod.data.remote.api.ModernApiService
import com.example.eighteighttwod.data.remote.api.MyanmarLotApiService
import com.example.eighteighttwod.data.remote.api.OmenApiService
import com.example.eighteighttwod.data.remote.api.ThaiLotClientApiService
import com.example.eighteighttwod.data.remote.api.TwoDHistoryApiService
import com.example.eighteighttwod.data.remote.api.UpdateResultApiService
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
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import okhttp3.Protocol
import java.net.URI

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
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
            .retryOnConnectionFailure(true)
            .protocols(listOf(Protocol.HTTP_1_1))
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
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

    @Provides
    @Singleton
    fun provideOmenApiService(retrofit: Retrofit): OmenApiService{
        return retrofit.create(OmenApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTwoDHistoryApiService(retrofit: Retrofit): TwoDHistoryApiService{
        return retrofit.create(TwoDHistoryApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLuckyApiService(retrofit: Retrofit): LuckyApiService{
        return retrofit.create(LuckyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUpdateResultApiService(retrofit: Retrofit): UpdateResultApiService {
        return retrofit.create(UpdateResultApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideModernApiService(retrofit: Retrofit): ModernApiService {
        return retrofit.create(ModernApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMyanmarLotApiService(retrofit: Retrofit): MyanmarLotApiService {
        return retrofit.create(MyanmarLotApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideThaiLotClientApiService(retrofit: Retrofit): ThaiLotClientApiService {
        return retrofit.create(ThaiLotClientApiService::class.java)
    }

    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideSocket(): Socket{
        return try {
            val uri =URI.create("http://mmsub.asia:8080")
//            val uri =URI.create(Constants.BASE_URL)

            val option = IO.Options.builder()
                .setTransports(arrayOf("websocket"))
                .setUpgrade(false)
                .setReconnection(true)
                .setReconnectionAttempts(Int.MAX_VALUE)
                .setReconnectionDelay(1000)
                .setReconnectionDelayMax(5000) // Max 5s ပဲစောင့်ခိုင်းမယ်
                .setTimeout(10000) // Connection Timeout 10s
                .build()

                IO.socket(uri,option)

        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }

}