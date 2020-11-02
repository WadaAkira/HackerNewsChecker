package com.example.hackernewschecker.di

import com.example.hackernewschecker.usecase.HackerNewsUseCase
import com.example.hackernewschecker.usecase.impl.HackerNewsUseCaseImpl
import com.example.hackernewschecker.usecase.repository.Repository
import com.example.hackernewschecker.usecase.repository.impl.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI するクラスを定義する
 */
@Module
class Module {
    @Singleton
    @Provides
    fun provideRepository(): Repository {
        return RepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideHackerNewsUseCase(repository: Repository): HackerNewsUseCase {
        return HackerNewsUseCaseImpl(repository)
    }
}