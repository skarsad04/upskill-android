package com.hcl.upskill.di

import com.hcl.upskill.base.BaseDispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Reusable
    fun provideDispatcher(): BaseDispatcher = BaseDispatcher()

}