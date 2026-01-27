package com.example.eighteighttwod.di

import com.example.eighteighttwod.data.repository.LiveRepository
import com.example.eighteighttwod.data.repository.LiveRepositoryImp
import com.example.eighteighttwod.data.repository.LuckyRepository
import com.example.eighteighttwod.data.repository.LuckyRepositoryImp
import com.example.eighteighttwod.data.repository.ModernRepository
import com.example.eighteighttwod.data.repository.ModernRepositoryImp
import com.example.eighteighttwod.data.repository.MyanmarLotRepository
import com.example.eighteighttwod.data.repository.MyanmarLotRepositoryImpl
import com.example.eighteighttwod.data.repository.OmenRepository
import com.example.eighteighttwod.data.repository.OmenRepositoryImp
import com.example.eighteighttwod.data.repository.ThaiLotClientRepository
import com.example.eighteighttwod.data.repository.ThaiLotClientRepositoryImpl
import com.example.eighteighttwod.data.repository.UpdateResultRepository
import com.example.eighteighttwod.data.repository.UpdateResultRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindLiveRepository(
        liveRepositoryImp: LiveRepositoryImp
    ): LiveRepository

    @Binds
    @Singleton
    abstract fun bindOmenRepository(
        omenRepository: OmenRepositoryImp
    ): OmenRepository

    @Binds
    @Singleton
    abstract fun bindLuckyRepository(
        luckyRepository: LuckyRepositoryImp
    ): LuckyRepository

    @Binds
    @Singleton
    abstract fun bindUpdateResultRepository(
        impl: UpdateResultRepositoryImpl
    ): UpdateResultRepository

    @Binds
    @Singleton
    abstract fun bindModernRepository(
        modernRepository: ModernRepositoryImp
    ): ModernRepository

    @Binds
    @Singleton
    abstract fun bindMyanmarLotRepository(
        impl: MyanmarLotRepositoryImpl
    ): MyanmarLotRepository

    @Binds
    @Singleton
    abstract fun bindThaiLotClientRepository(
        impl: ThaiLotClientRepositoryImpl
    ): ThaiLotClientRepository

}