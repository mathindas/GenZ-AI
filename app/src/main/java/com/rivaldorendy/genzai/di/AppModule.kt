package com.rivaldorendy.genzai.di

import android.content.Context
import com.rivaldorendy.genzai.data.api.GeminiApiClient
import com.rivaldorendy.genzai.data.api.GeminiApiService
import com.rivaldorendy.genzai.data.repository.ExcuseRepository
import com.rivaldorendy.genzai.data.repository.SettingsRepository
import com.rivaldorendy.genzai.data.repository.ToolsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGeminiApiService(): GeminiApiService {
        return GeminiApiClient.apiService
    }

    @Provides
    @Singleton
    fun provideExcuseRepository(apiService: GeminiApiService): ExcuseRepository {
        return ExcuseRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideToolsRepository(): ToolsRepository {
        return ToolsRepository()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }
} 