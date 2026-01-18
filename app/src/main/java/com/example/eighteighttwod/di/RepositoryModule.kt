package com.example.eighteighttwod.di

import com.example.eighteighttwod.data.repository.LiveRepository
import com.example.eighteighttwod.data.repository.LiveRepositoryImp
import com.example.eighteighttwod.data.repository.OmenRepository
import com.example.eighteighttwod.data.repository.OmenRepositoryImp
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
}