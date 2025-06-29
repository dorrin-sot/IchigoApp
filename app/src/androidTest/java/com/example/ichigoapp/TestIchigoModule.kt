package com.example.ichigoapp

import com.example.ichigoapp.service.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [IchigoModule::class]
)
object TestIchigoModule {
  @Provides
  @Singleton
  fun provideRepository(): Repository = mockk(relaxed = true)

  @Provides
  @Singleton
  fun provideIoDispatcher(): CoroutineDispatcher = mockk(relaxed = true)
}